package ru.practicum.controller.private_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentShortDto;
import ru.practicum.service.private_service.PrivateCommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
public class PrivateCommentController {
    private final PrivateCommentService commentService;

    @GetMapping("/comments")
    List<CommentShortDto> getByUserId(@PathVariable long userId, @RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "10") int size) {
        List<CommentShortDto> comments = commentService.getByUserId(userId, from, size);
        log.info("Получен список опубликованных комментариев пользователя с ID = {}.", userId);

        return comments;
    }

    @GetMapping("/comments/{commId}")
    public CommentDto getById(@PathVariable long userId, @PathVariable long commId) {
        CommentDto comment = commentService.getById(userId, commId);
        log.info("Получен комментарий '{}'.", comment.getText());

        return comment;
    }

    @PostMapping("/events/{eventId}/comments")
    public CommentDto createComment(@PathVariable long userId, @PathVariable long eventId,
                                    @RequestBody @Valid CommentDto commentDto) {
        CommentDto createdComment = commentService.create(userId, eventId, commentDto);
        log.info("Создан комментарий '{}'.", createdComment.getText());

        return createdComment;
    }

    @PatchMapping("/events/{eventId}/comments")
    public CommentDto updateComment(@PathVariable long userId, @PathVariable long eventId,
                                    @RequestBody @Valid CommentDto commentDto) {
        CommentDto updatedComment = commentService.update(userId, eventId, commentDto);
        log.info("Данные комментария '{}' обновлены.", updatedComment.getText());

        return updatedComment;
    }

    @DeleteMapping("/comments/{commId}")
    public void deleteComment(@PathVariable long userId, @PathVariable long commId) {
        commentService.delete(userId, commId);
        log.info("Комментарий с ID = {} удалён.", commId);
    }
}
