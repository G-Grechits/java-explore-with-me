package ru.practicum.mapper;

import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.entity.Request;

public class RequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(request.getId(), request.getRequester().getId(), request.getEvent().getId(),
                DateTimeMapper.toString(request.getCreated()), request.getStatus().name());
    }
}
