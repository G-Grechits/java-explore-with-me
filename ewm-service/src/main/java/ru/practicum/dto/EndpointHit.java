package ru.practicum.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EndpointHit {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
