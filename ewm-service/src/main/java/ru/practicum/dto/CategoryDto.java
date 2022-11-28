package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    @NotBlank
    private String name;
}
