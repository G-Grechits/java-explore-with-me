package ru.practicum.service.private_service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.State;
import ru.practicum.dto.*;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.entity.Request;
import ru.practicum.entity.User;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.repository.*;
import ru.practicum.service.EventStatsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.DateTimeMapper.toLocalDateTime;
import static ru.practicum.mapper.EventMapper.*;
import static ru.practicum.mapper.RequestMapper.toParticipationRequestDto;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final EventStatsService statsService;

    @Override
    public List<EventShortDto> get(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findAllByInitiatorId(userId, pageable);

        return events.stream()
                .map(e -> toEventShortDto(e, statsService.getViews(e.getId()),
                        statsService.getConfirmedRequests(e.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getById(long id, long userId) {
        Event event = getEventFromRepository(id, userId);

        return toEventFullDto(event, statsService.getViews(id), statsService.getConfirmedRequests(id));
    }

    @Override
    public EventFullDto create(long userId, NewEventDto eventDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с ID = %d не найден.", userId)));
        throwIfDateIsWrong(toLocalDateTime(eventDto.getEventDate()));
        locationRepository.save(eventDto.getLocation());
        Category category = getCategoryFromRepository(eventDto.getCategory());
        Event event = eventRepository.save(toEvent(eventDto, category, user));

        return toEventFullDto(event, 0, 0);
    }

    @Override
    public EventFullDto update(long userId, UpdateEventRequest event) {
        Event formerEvent = getEventFromRepository(event.getEventId(), userId);
        switch (formerEvent.getState()) {
            case PUBLISHED:
                throw new BadRequestException("Событие уже опубликовано.");
            case PENDING:
                break;
            case CANCELED:
                formerEvent.setState(State.PENDING);
                break;
        }
        throwIfDateIsWrong(formerEvent.getEventDate());
        if (event.getEventDate() != null) {
            LocalDateTime eventDate = toLocalDateTime(event.getEventDate());
            throwIfDateIsWrong(eventDate);
            formerEvent.setEventDate(eventDate);
        }
        formerEvent.setTitle(event.getTitle() != null ? event.getTitle() : formerEvent.getTitle());
        formerEvent.setCategory(event.getCategory() != null ? getCategoryFromRepository(event.getCategory())
                : formerEvent.getCategory());
        formerEvent.setAnnotation(event.getAnnotation() != null ? event.getAnnotation() : formerEvent.getAnnotation());
        formerEvent.setDescription(event.getDescription() != null ? event.getDescription() : formerEvent.getDescription());
        formerEvent.setPaid(event.getPaid() != null ? event.getPaid() : formerEvent.getPaid());
        formerEvent.setParticipantLimit(event.getParticipantLimit() != null ? event.getParticipantLimit()
                : formerEvent.getParticipantLimit());
        Event updatedEvent = eventRepository.save(formerEvent);

        return toEventFullDto(updatedEvent, statsService.getViews(event.getEventId()),
                statsService.getConfirmedRequests(event.getEventId()));
    }

    @Override
    public EventFullDto cancel(long id, long userId) {
        Event event = getEventFromRepository(id, userId);
        if (event.getState() != State.PENDING) {
            throw new BadRequestException("Отменить можно только событие в состоянии ожидания модерации.");
        }
        event.setState(State.CANCELED);
        Event cancelledEvent = eventRepository.save(event);

        return toEventFullDto(cancelledEvent, statsService.getViews(id), statsService.getConfirmedRequests(id));
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(long id, long userId) {
        getEventFromRepository(id, userId);

        return requestRepository.findAllByEventId(id).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequest(long id, long userId, long reqId) {
        Request request = requestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException(String.format("Заявка с ID = %d не найдена.", reqId)));
        Event event = request.getEvent();
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new BadRequestException("Подтверждение заявок для данного события не требуется");
        }
        if (event.getInitiator().getId() != userId) {
            throw new BadRequestException("Подтвердить заявку на участие может только инициатор события.");
        }
        int confirmedRequestCount = requestRepository.findAllByEventIdAndStatus(id, State.CONFIRMED).size();
        if (confirmedRequestCount == event.getParticipantLimit()) {
            throw new BadRequestException("Исчерпан лимит заявок на участие в событии.");
        }
        request.setStatus(State.CONFIRMED);
        if (event.getParticipantLimit() == confirmedRequestCount + 1) {
            requestRepository.findAllByEventIdAndStatus(id, State.PENDING).stream()
                    .peek(r -> r.setStatus(State.CANCELED))
                    .forEach(requestRepository::save);
        }
        Request confirmedRequest = requestRepository.save(request);

        return toParticipationRequestDto(confirmedRequest);
    }

    @Override
    public ParticipationRequestDto rejectRequest(long id, long userId, long reqId) {
        Request request = requestRepository.findByIdAndEventId(reqId, id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Заявка с ID = %d на участие в событии с ID = %d не найдена.", reqId, id)));
        if (request.getEvent().getInitiator().getId() != userId) {
            throw new BadRequestException("Отклонить заявку на участие может только инициатор события.");
        }
        request.setStatus(State.REJECTED);
        Request rejectedRequest = requestRepository.save(request);

        return toParticipationRequestDto(rejectedRequest);
    }

    private Event getEventFromRepository(long id, long userId) {
        return eventRepository.findByIdAndInitiatorId(id, userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие с ID = %d и инициатором с ID = %d не найдено.", id, userId)));
    }

    private Category getCategoryFromRepository(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Категория с ID = %d не найдена.", id)));
    }

    private void throwIfDateIsWrong(LocalDateTime eventDate) {
        if (eventDate.minusHours(2).isBefore(LocalDateTime.now())) {
            throw new ValidationException("Дата начала события должна быть не ранее, чем за два часа от текущего момента");
        }
    }
}
