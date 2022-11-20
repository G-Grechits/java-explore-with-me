package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.entity.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class EventFullDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String annotation;
    private String description;
    @NotNull
    private CategoryDto category;
    private String createdOn;
    private String publishedOn;
    @NotBlank
    private String eventDate;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private Location location;
    @NotNull
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String state;
    private Integer views;
    private Integer confirmedRequests;
}
