package ru.practicum.service.admin_service;

import ru.practicum.State;
import ru.practicum.dto.AdminUpdateEventRequest;
import ru.practicum.dto.EventFullDto;

import java.util.List;

public interface AdminEventService {

    List<EventFullDto> get(List<Long> users, List<State> states, List<Long> categories, String rangeStart,
                           String rangeEnd, int from, int size);

    EventFullDto update(long id, AdminUpdateEventRequest updateEventRequest);

    EventFullDto publish(long id);

    EventFullDto reject(long id);
}
