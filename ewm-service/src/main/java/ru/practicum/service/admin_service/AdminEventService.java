package ru.practicum.service.admin_service;

import ru.practicum.State;
import ru.practicum.dto.AdminUpdateEventRequest;
import ru.practicum.dto.EventFullDto;

import java.util.List;

public interface AdminEventService {

    List<EventFullDto> getEvents(List<Long> users, List<State> states, List<Long> categories, String rangeStart,
                                 String rangeEnd, int from, int size);

    EventFullDto updateEvent(long id, AdminUpdateEventRequest updateEventRequest);

    EventFullDto publishEvent(long id);

    EventFullDto rejectEvent(long id);
}
