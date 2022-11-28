package ru.practicum.service.public_service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.State;
import ru.practicum.client.HitClient;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.entity.Event;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.DateTimeMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.EventStatsService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final EventStatsService statsService;
    private final HitClient hitClient;

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                         String rangeEnd, Boolean onlyAvailable, String sort, int from, int size,
                                         HttpServletRequest request) {
        Sort sorted = Sort.unsorted();
        if (sort != null) {
            if (sort.equals("EVENT_DATE")) {
                sorted = Sort.by("eventDate");
            }
            if (sort.equals("VIEWS")) {
                sorted = Sort.by("views");
            }
        }
        Pageable pageable = PageRequest.of(from / size, size, sorted);
        List<Event> events = eventRepository.findAllByParams(text, categories, paid,
                rangeStart != null ? DateTimeMapper.toLocalDateTime(rangeStart) : LocalDateTime.now(),
                rangeEnd != null ? DateTimeMapper.toLocalDateTime(rangeEnd) : LocalDateTime.now().plusYears(1), pageable);
        if (onlyAvailable) {
            events = events.stream()
                    .filter(e -> e.getParticipantLimit() > statsService.getConfirmedRequests(e.getId()))
                    .collect(Collectors.toList());
        }
        events.forEach(e -> hitClient.saveStats(EndpointHit.builder()
                .app("ewm-service")
                .uri(request.getRequestURI() + "/" + e.getId())
                .ip(request.getRemoteAddr())
                .timestamp(DateTimeMapper.toString(LocalDateTime.now()))
                .build()));
        return events.stream()
                .map(e -> EventMapper.toEventShortDto(e, statsService.getViews(e.getId()),
                        statsService.getConfirmedRequests(e.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventById(long id, HttpServletRequest request) {
        Event event = eventRepository.findByIdAndState(id, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие с ID = %d не найдено или не было опубликовано.", id)));
        hitClient.saveStats(EndpointHit.builder()
                .app("ewm-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(DateTimeMapper.toString(LocalDateTime.now()))
                .build());
        return EventMapper.toEventFullDto(event, statsService.getViews(id), statsService.getConfirmedRequests(id));
    }
}
