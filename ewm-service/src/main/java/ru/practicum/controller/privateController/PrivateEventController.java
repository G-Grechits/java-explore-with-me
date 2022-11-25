package ru.practicum.controller.privateController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.service.privateService.PrivateEventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final PrivateEventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable long userId, @RequestParam(defaultValue = "0") int from,
                                         @RequestParam(defaultValue = "10") int size) {
        List<EventShortDto> events = eventService.getEvents(userId, from, size);
        log.info("Получен список событий пользователя.");
        return events;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable long userId, @PathVariable long eventId) {
        EventFullDto event = eventService.getEventById(eventId, userId);
        log.info("Получено событие '{}'.", event.getTitle());
        return event;
    }

    @PostMapping
    public EventFullDto createEvent(@PathVariable long userId, @RequestBody @Valid NewEventDto eventDto) {
        EventFullDto createdEvent = eventService.createEvent(userId, eventDto);
        log.info("Создано событие '{}'.", createdEvent.getTitle());
        return createdEvent;
    }

    @PatchMapping
    public EventFullDto updateEvent(@PathVariable long userId, @RequestBody @Valid UpdateEventRequest eventDto) {
        EventFullDto updatedEvent = eventService.updateEvent(userId, eventDto);
        log.info("Данные события '{}' обновлены.", updatedEvent.getTitle());
        return updatedEvent;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable long userId, @PathVariable long eventId) {
        EventFullDto cancelledEvent = eventService.cancelEvent(eventId, userId);
        log.info("Событие '{}' отменено.", cancelledEvent.getTitle());
        return cancelledEvent;
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable long userId, @PathVariable long eventId) {
        List<ParticipationRequestDto> eventRequests = eventService.getEventRequests(eventId, userId);
        log.info("Получен список заявок на участие в событии.");
        return eventRequests;
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable long userId, @PathVariable long eventId,
                                                  @PathVariable long reqId) {
        ParticipationRequestDto confirmedRequest = eventService.confirmRequest(eventId, userId, reqId);
        log.info("Заявка на участие в событии подтверждена.");
        return confirmedRequest;
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable long userId, @PathVariable long eventId,
                                                 @PathVariable long reqId) {
        ParticipationRequestDto rejectedRequest = eventService.rejectRequest(eventId, userId, reqId);
        log.info("Заявка на участие в событии отклонена.");
        return rejectedRequest;
    }
}
