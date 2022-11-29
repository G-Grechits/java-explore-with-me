package ru.practicum.service.admin_service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.State;
import ru.practicum.dto.AdminUpdateEventRequest;
import ru.practicum.dto.EventFullDto;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.EventStatsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.DateTimeMapper.toLocalDateTime;
import static ru.practicum.mapper.EventMapper.toEventFullDto;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final EventStatsService statsService;

    @Override
    public List<EventFullDto> get(List<Long> users, List<State> states, List<Long> categories, String rangeStart,
                                  String rangeEnd, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateBetween(users,
                states != null ? states : new ArrayList<>(), categories,
                rangeStart != null ? toLocalDateTime(rangeStart) : null,
                rangeEnd != null ? toLocalDateTime(rangeEnd) : null, pageable);

        return events.stream()
                .map(e -> toEventFullDto(
                        e, statsService.getViews(e.getId()), statsService.getConfirmedRequests(e.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto update(long id, AdminUpdateEventRequest event) {
        Event formerEvent = getEventFromRepository(id);
        formerEvent.setTitle(event.getTitle() != null ? event.getTitle() : formerEvent.getTitle());
        formerEvent.setAnnotation(event.getAnnotation() != null ? event.getAnnotation() : formerEvent.getAnnotation());
        formerEvent.setDescription(event.getDescription() != null ? event.getDescription() : formerEvent.getDescription());
        formerEvent.setEventDate(event.getEventDate() != null ? toLocalDateTime(event.getEventDate())
                : formerEvent.getEventDate());
        formerEvent.setLocation(event.getLocation() != null ? event.getLocation() : formerEvent.getLocation());
        formerEvent.setPaid(event.getPaid() != null ? event.getPaid() : formerEvent.getPaid());
        formerEvent.setParticipantLimit(event.getParticipantLimit() != null ? event.getParticipantLimit()
                : formerEvent.getParticipantLimit());
        formerEvent.setRequestModeration(event.getRequestModeration() != null ? event.getRequestModeration()
                : formerEvent.getRequestModeration());
        if (event.getCategory() != null) {
            Category category = categoryRepository.findById(event.getCategory())
                    .orElseThrow(() -> new NotFoundException(
                            String.format("Категория с ID = %d не найдена.", event.getCategory())));
            formerEvent.setCategory(category);
        }
        Event updatedEvent = eventRepository.save(formerEvent);

        return toEventFullDto(updatedEvent, statsService.getViews(id), statsService.getConfirmedRequests(id));
    }

    @Override
    public EventFullDto publish(long id) {
        Event event = getEventFromRepository(id);
        if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new ValidationException("Дата начала события должна быть не ранее, чем за час от даты публикации.");
        }
        if (!event.getState().equals(State.PENDING)) {
            throw new ValidationException("Событие должно быть в состоянии ожидания публикации.");
        }
        event.setState(State.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        Event publishedEvent = eventRepository.save(event);

        return toEventFullDto(publishedEvent, statsService.getViews(id), statsService.getConfirmedRequests(id));
    }

    @Override
    public EventFullDto reject(long id) {
        Event event = getEventFromRepository(id);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("Событие уже опубликовано.");
        }
        event.setState(State.CANCELED);
        Event cancelledEvent = eventRepository.save(event);

        return toEventFullDto(cancelledEvent, statsService.getViews(id), statsService.getConfirmedRequests(id));
    }

    private Event getEventFromRepository(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Событие с ID = %d не найдено.", id)));
    }
}
