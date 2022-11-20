package ru.practicum.service.publicAPI;

import ru.practicum.dto.EventFullDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicEventService {

    List<EventFullDto> getAllEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                    String rangeEnd, Boolean onlyAvailable, String sort, int from, int size,
                                    HttpServletRequest request);

    EventFullDto getEventById(long id, HttpServletRequest request);
}
