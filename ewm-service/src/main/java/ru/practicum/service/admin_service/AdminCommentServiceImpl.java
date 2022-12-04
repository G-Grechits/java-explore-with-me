package ru.practicum.service.admin_service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.State;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentShortDto;
import ru.practicum.entity.Comment;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.CommentMapper.toCommentDto;
import static ru.practicum.mapper.DateTimeMapper.toLocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentShortDto> get(List<Long> users, List<Long> events, List<State> statuses, String rangeStart,
                                     String rangeEnd, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Comment> comments = commentRepository.findAllByCommentatorIdInAndEventIdInAndStatusInAndCreatedBetween(
                users != null ? users : new ArrayList<>(), events != null ? events : new ArrayList<>(),
                statuses != null ? statuses : new ArrayList<>(),
                rangeStart != null ? toLocalDateTime(rangeStart) : LocalDateTime.now().minusYears(1),
                rangeEnd != null ? toLocalDateTime(rangeEnd) : LocalDateTime.now(), pageable);

        return comments.stream()
                .map(CommentMapper::toCommentShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto publish(long eventId, long commId) {
        Comment comment = getCommentFromRepository(commId, eventId);
        if (!comment.getStatus().equals(State.PENDING)) {
            throw new ValidationException("Комментарий должен быть в состоянии ожидания публикации.");
        }
        comment.setStatus(State.PUBLISHED);
        comment.setPublished(LocalDateTime.now());
        Comment publishedComment = commentRepository.save(comment);

        return toCommentDto(publishedComment);
    }

    @Override
    public CommentDto cancel(long eventId, long commId) {
        Comment comment = getCommentFromRepository(commId, eventId);
        if (comment.getStatus().equals(State.PUBLISHED)) {
            throw new ValidationException("Комментарий уже опубликован.");
        }
        comment.setStatus(State.CANCELED);
        Comment cancelledComment = commentRepository.save(comment);

        return toCommentDto(cancelledComment);
    }

    private Comment getCommentFromRepository(long id, long eventId) {
        return commentRepository.findByIdAndEventId(id, eventId)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Комментарий с ID = %d к событию с ID = %d не найден.", id, eventId)));
    }
}
