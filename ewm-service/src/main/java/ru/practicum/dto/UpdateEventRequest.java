package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UpdateEventRequest {
    @NotNull
    private Long eventId;
    @Length(min = 3, max = 120)
    private String title;
    @Length(min = 20, max = 2000)
    private String annotation;
    @Length(min = 20, max = 7000)
    private String description;
    private Long category;
    private String eventDate;
    private Boolean paid;
    private Integer participantLimit;
}
