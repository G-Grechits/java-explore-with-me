package ru.practicum.service.private_service;

import ru.practicum.dto.*;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> getEvents(long userId, int from, int size);

    EventFullDto getEventById(long id, long userId);

    EventFullDto createEvent(long userId, NewEventDto eventDto);

    EventFullDto updateEvent(long userId, UpdateEventRequest eventDto);

    EventFullDto cancelEvent(long id, long userId);

    List<ParticipationRequestDto> getEventRequests(long id, long userId);

    ParticipationRequestDto confirmRequest(long id, long userId, long reqId);

    ParticipationRequestDto rejectRequest(long id, long userId, long reqId);
}
