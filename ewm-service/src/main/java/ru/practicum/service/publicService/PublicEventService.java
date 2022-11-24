package ru.practicum.service.publicService;

import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicEventService {

    List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                     String rangeEnd, Boolean onlyAvailable, String sort, int from, int size,
                                     HttpServletRequest request);

    EventFullDto getEventById(long id, HttpServletRequest request);
}
