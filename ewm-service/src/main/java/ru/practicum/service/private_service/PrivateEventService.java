package ru.practicum.service.private_service;

import ru.practicum.dto.*;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> get(long userId, int from, int size);

    EventFullDto getById(long id, long userId);

    EventFullDto create(long userId, NewEventDto eventDto);

    EventFullDto update(long userId, UpdateEventRequest eventDto);

    EventFullDto cancel(long id, long userId);

    List<ParticipationRequestDto> getEventRequests(long id, long userId);

    ParticipationRequestDto confirmRequest(long id, long userId, long reqId);

    ParticipationRequestDto rejectRequest(long id, long userId, long reqId);
}
