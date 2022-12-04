package ru.practicum.controller.admin_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.State;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentShortDto;
import ru.practicum.service.admin_service.AdminCommentService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCommentController {
    private final AdminCommentService commentService;

    @GetMapping("/comments")
    List<CommentShortDto> getComments(@RequestParam(required = false) List<Long> users,
                                      @RequestParam(required = false) List<Long> events,
                                      @RequestParam(required = false) List<State> statuses,
                                      @RequestParam(required = false) String rangeStart,
                                      @RequestParam(required = false) String rangeEnd,
                                      @RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "10") int size) {
        List<CommentShortDto> comments = commentService.get(users, events, statuses, rangeStart, rangeEnd, from, size);
        log.info("Получен список комментариев.");

        return comments;
    }

    @PatchMapping("/events/{eventId}/comments/{commId}/publish")
    public CommentDto publishComment(@PathVariable long eventId, @PathVariable long commId) {
        CommentDto publishedComment = commentService.publish(eventId, commId);
        log.info("Комментарий пользователя {} опубликован.", publishedComment.getCommentator().getName());

        return publishedComment;
    }

    @PatchMapping("/events/{eventId}/comments/{commId}/cancel")
    public CommentDto cancelComment(@PathVariable long eventId, @PathVariable long commId) {
        CommentDto cancelledComment = commentService.cancel(eventId, commId);
        log.info("Комментарий пользователя {} отклонён.", cancelledComment.getCommentator().getName());

        return cancelledComment;
    }
}
