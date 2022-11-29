package ru.practicum.controller.admin_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.State;
import ru.practicum.dto.AdminUpdateEventRequest;
import ru.practicum.dto.EventFullDto;
import ru.practicum.service.admin_service.AdminEventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {
    private final AdminEventService eventService;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<State> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(required = false) String rangeStart,
                                        @RequestParam(required = false) String rangeEnd,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        List<EventFullDto> events = eventService.get(users, states, categories, rangeStart, rangeEnd, from, size);
        log.info("Получен список событий.");

        return events;
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable long eventId, @RequestBody @Valid AdminUpdateEventRequest event) {
        EventFullDto updatedEvent = eventService.update(eventId, event);
        log.info("Данные события '{}' обновлены.", updatedEvent.getTitle());

        return updatedEvent;
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable long eventId) {
        EventFullDto publishedEvent = eventService.publish(eventId);
        log.info("Событие '{}' опубликовано.", publishedEvent.getTitle());

        return publishedEvent;
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable long eventId) {
        EventFullDto rejectedEvent = eventService.reject(eventId);
        log.info("Событие '{}' отклонено.", rejectedEvent.getTitle());

        return rejectedEvent;
    }
}
