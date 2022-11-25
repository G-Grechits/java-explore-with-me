package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.entity.Location;

@Getter
@AllArgsConstructor
public class EventFullDto {
    private Long id;
    private String title;
    private String annotation;
    private String description;
    private CategoryDto category;
    private String createdOn;
    private String publishedOn;
    private String eventDate;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String state;
    private Integer views;
    private Integer confirmedRequests;
}
