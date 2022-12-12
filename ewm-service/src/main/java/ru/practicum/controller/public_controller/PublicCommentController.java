package ru.practicum.controller.public_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CommentShortDto;
import ru.practicum.service.public_service.PublicCommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/comments")
public class PublicCommentController {
    private final PublicCommentService commentService;

    @GetMapping("/events/{eventId}")
    public List<CommentShortDto> getCommentsByEventId(@PathVariable @Positive long eventId,
                                                      @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                      @RequestParam(defaultValue = "10") @Positive int size) {
        List<CommentShortDto> comments = commentService.getByEventId(eventId, from, size);
        log.info("Получен список опубликованных комментариев к событию с ID = {}.", eventId);

        return comments;
    }

    @GetMapping("/{commId}")
    public CommentShortDto getCommentById(@PathVariable @Positive long commId) {
        CommentShortDto comment = commentService.getById(commId);
        log.info("Получен комментарий '{}'.", comment.getText());

        return comment;
    }
}
