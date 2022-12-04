package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentShortDto {
    private Long id;
    private String text;
    private String commentator;
    private String published;
}
