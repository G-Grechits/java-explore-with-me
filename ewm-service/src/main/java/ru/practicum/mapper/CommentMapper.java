package ru.practicum.mapper;

import ru.practicum.State;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentShortDto;
import ru.practicum.entity.Comment;
import ru.practicum.entity.Event;
import ru.practicum.entity.User;

import java.time.LocalDateTime;

import static ru.practicum.mapper.DateTimeMapper.toTextDateTime;
import static ru.practicum.mapper.UserMapper.toUserShortDto;

public class CommentMapper {

    public static Comment toComment(CommentDto commentDto, User commentator, Event event) {
        return new Comment(null, commentDto.getText(), commentator, event, LocalDateTime.now(), null,
                State.PENDING);
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), toUserShortDto(comment.getCommentator()),
                comment.getEvent().getId(), toTextDateTime(comment.getCreated()),
                comment.getPublished() != null ? toTextDateTime(comment.getPublished()) : null, comment.getStatus().name());
    }

    public static CommentShortDto toCommentShortDto(Comment comment) {
        return new CommentShortDto(comment.getId(), comment.getText(), comment.getCommentator().getName(),
                comment.getPublished() != null ? toTextDateTime(comment.getPublished()) : null);
    }
}
