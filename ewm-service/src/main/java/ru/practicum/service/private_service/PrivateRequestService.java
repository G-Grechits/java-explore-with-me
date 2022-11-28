package ru.practicum.service.private_service;

import ru.practicum.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestService {

    List<ParticipationRequestDto> getRequests(long userId);

    ParticipationRequestDto createRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);
}
