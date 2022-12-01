package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@AllArgsConstructor
public class NewCompilationDto {
    @NotBlank
    private String title;

    private Boolean pinned;
    private Set<Long> events;
}
