package ru.practicum.controller.private_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentShortDto;
import ru.practicum.service.private_service.PrivateCommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
public class PrivateCommentController {
    private final PrivateCommentService commentService;

    @GetMapping("/comments")
    List<CommentShortDto> getCommentsByUserId(@PathVariable @Positive long userId,
                                              @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                              @RequestParam(defaultValue = "10") @Positive int size) {
        List<CommentShortDto> comments = commentService.getByUserId(userId, from, size);
        log.info("Получен список опубликованных комментариев пользователя с ID = {}.", userId);

        return comments;
    }

    @GetMapping("/comments/{commId}")
    public CommentDto getCommentById(@PathVariable @Positive long userId, @PathVariable @Positive long commId) {
        CommentDto comment = commentService.getById(userId, commId);
        log.info("Получен комментарий '{}'.", comment.getText());

        return comment;
    }

    @PostMapping("/events/{eventId}/comments")
    public CommentDto createComment(@PathVariable @Positive long userId, @PathVariable @Positive long eventId,
                                    @RequestBody @Valid CommentDto commentDto) {
        CommentDto createdComment = commentService.create(userId, eventId, commentDto);
        log.info("Создан комментарий '{}'.", createdComment.getText());

        return createdComment;
    }

    @PatchMapping("/events/{eventId}/comments")
    public CommentDto updateComment(@PathVariable @Positive long userId, @PathVariable @Positive long eventId,
                                    @RequestBody @Valid CommentDto commentDto) {
        CommentDto updatedComment = commentService.update(userId, eventId, commentDto);
        log.info("Данные комментария '{}' обновлены.", updatedComment.getText());

        return updatedComment;
    }

    @DeleteMapping("/comments/{commId}")
    public void deleteComment(@PathVariable @Positive long userId, @PathVariable @Positive long commId) {
        commentService.delete(userId, commId);
        log.info("Комментарий с ID = {} удалён.", commId);
    }
}
