package ru.practicum.mapper;

import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.entity.Request;

import static ru.practicum.mapper.DateTimeMapper.toTextDateTime;

public class RequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(request.getId(), request.getRequester().getId(), request.getEvent().getId(),
                toTextDateTime(request.getCreated()), request.getStatus().name());
    }
}
