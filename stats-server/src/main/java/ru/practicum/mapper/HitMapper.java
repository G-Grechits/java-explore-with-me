package ru.practicum.mapper;

import ru.practicum.Hit;
import ru.practicum.dto.EndpointHit;

import static ru.practicum.mapper.DateTimeMapper.toLocalDateTime;
import static ru.practicum.mapper.DateTimeMapper.toTextDateTime;

public class HitMapper {

    public static Hit toHit(EndpointHit endpointHit) {
        return new Hit(endpointHit.getId(), endpointHit.getApp(), endpointHit.getUri(), endpointHit.getIp(),
                toLocalDateTime(endpointHit.getTimestamp()));
    }

    public static EndpointHit toEndpointHit(Hit hit) {
        return new EndpointHit(hit.getId(), hit.getApp(), hit.getUri(), hit.getIp(), toTextDateTime(hit.getTimestamp()));
    }
}
