package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CommentDto {
    private Long id;

    @NotBlank
    @Length(max = 2000)
    private String text;

    private UserShortDto commentator;
    private Long event;
    private String created;
    private String published;
    private String status;
}
