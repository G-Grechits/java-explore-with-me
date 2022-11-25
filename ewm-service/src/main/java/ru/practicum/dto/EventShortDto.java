package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventShortDto {
    private Long id;
    private String title;
    private String annotation;
    private CategoryDto category;
    private String eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private Integer views;
    private Integer confirmedRequests;
}
