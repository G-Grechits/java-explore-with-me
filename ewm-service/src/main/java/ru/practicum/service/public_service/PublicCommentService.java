package ru.practicum.service.public_service;

import ru.practicum.dto.CommentShortDto;

import java.util.List;

public interface PublicCommentService {

    List<CommentShortDto> getByEventId(long eventId, int from, int size);
}
