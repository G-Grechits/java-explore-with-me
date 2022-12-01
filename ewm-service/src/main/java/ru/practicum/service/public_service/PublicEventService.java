package ru.practicum.service.public_service;

import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicEventService {

    List<EventShortDto> get(String text, List<Long> categories, Boolean paid, String rangeStart,
                            String rangeEnd, Boolean onlyAvailable, String sort, int from, int size,
                            HttpServletRequest request);

    EventFullDto getById(long id, HttpServletRequest request);
}
