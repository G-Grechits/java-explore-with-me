package ru.practicum.service.publicAPI;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.State;
import ru.practicum.client.HitClient;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.EventFullDto;
import ru.practicum.entity.Event;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.DateTimeMapper;
import ru.practicum.repository.EventRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final HitClient hitClient;

    @Override
    public List<EventFullDto> getAllEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                           String rangeEnd, Boolean onlyAvailable, String sort, int from, int size,
                                           HttpServletRequest request) {
        return null;
    }

    @Override
    public EventFullDto getEventById(long id, HttpServletRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Событие с ID = %d не найдено.", id)));
        if (event.getState() != State.PUBLISHED) {
            throw new NotFoundException(String.format("Событие с ID = %d не опубликовано.", id));
        }
        hitClient.saveStats(EndpointHit.builder()
                .app("ewm-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(DateTimeMapper.toString(LocalDateTime.now())).build());
        return null;
    }
}
