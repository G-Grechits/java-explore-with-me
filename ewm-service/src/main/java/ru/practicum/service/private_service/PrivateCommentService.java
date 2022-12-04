package ru.practicum.service.private_service;

import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentShortDto;

import java.util.List;

public interface PrivateCommentService {

    List<CommentShortDto> getByUserId(long userId, int from, int size);

    CommentDto getById(long userId, long commId);

    CommentDto create(long userId, long eventId, CommentDto commentDto);

    CommentDto update(long userId, long eventId, CommentDto commentDto);

    void delete(long userId, long commId);
}
