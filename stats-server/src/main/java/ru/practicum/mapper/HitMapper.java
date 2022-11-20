package ru.practicum.mapper;

import ru.practicum.Hit;
import ru.practicum.dto.EndpointHit;

public class HitMapper {

    public static Hit toHit(EndpointHit endpointHit) {
        return new Hit(endpointHit.getId(), endpointHit.getApp(), endpointHit.getUri(), endpointHit.getIp(),
                DateTimeMapper.toLocalDateTime(endpointHit.getTimestamp()));
    }

    public static EndpointHit toEndpointHit(Hit hit) {
        return new EndpointHit(hit.getId(), hit.getApp(), hit.getUri(), hit.getIp(),
                DateTimeMapper.toString(hit.getTimestamp()));
    }
}
