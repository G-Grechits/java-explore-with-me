package ru.practicum.controller.public_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CommentShortDto;
import ru.practicum.service.public_service.PublicCommentService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/events/{eventId}/comments")
public class PublicCommentController {
    private final PublicCommentService commentService;

    @GetMapping
    public List<CommentShortDto> getCommentsByEventId(@PathVariable long eventId,
                                                      @RequestParam(defaultValue = "0") int from,
                                                      @RequestParam(defaultValue = "10") int size) {
        List<CommentShortDto> comments = commentService.getByEventId(eventId, from, size);
        log.info("Получен список опубликованных комментариев к событию с ID = {}.", eventId);

        return comments;
    }
}
