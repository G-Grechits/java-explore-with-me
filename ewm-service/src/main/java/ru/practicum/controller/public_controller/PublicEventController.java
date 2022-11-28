package ru.practicum.controller.public_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.service.public_service.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final PublicEventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(required = false) String text,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(required = false) Boolean paid,
                                         @RequestParam(required = false) String rangeStart,
                                         @RequestParam(required = false) String rangeEnd,
                                         @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(required = false) String sort,
                                         @RequestParam(defaultValue = "0") int from,
                                         @RequestParam(defaultValue = "10") int size,
                                         HttpServletRequest request) {
        List<EventShortDto> events = eventService.getEvents(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
        log.info("Получен список событий.");
        return events;
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable long id, HttpServletRequest request) {
        EventFullDto event = eventService.getEventById(id, request);
        log.info("Получено событие '{}'.", event.getTitle());
        return event;
    }
}
