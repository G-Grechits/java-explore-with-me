package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.entity.Location;

@Getter
@AllArgsConstructor
public class AdminUpdateEventRequest {
    private String title;
    private String annotation;
    private String description;
    private Long category;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
}
