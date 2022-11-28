package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.entity.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@AllArgsConstructor
public class NewEventDto {
    @NotBlank
    @Length(min = 3, max = 120)
    private String title;

    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;

    @NotBlank
    @Length(min = 20, max = 7000)
    private String description;

    @NotNull
    private Long category;

    @NotBlank
    private String eventDate;

    @NotNull
    private Location location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;
}
