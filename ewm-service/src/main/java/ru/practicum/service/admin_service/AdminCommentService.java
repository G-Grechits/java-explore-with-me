package ru.practicum.service.admin_service;

import ru.practicum.State;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentShortDto;

import java.util.List;

public interface AdminCommentService {

    List<CommentShortDto> get(List<Long> users, List<Long> events, List<State> statuses, String rangeStart,
                              String rangeEnd, int from, int size);

    CommentDto publish(long eventId, long commId);

    CommentDto cancel(long eventId, long commId);
}
