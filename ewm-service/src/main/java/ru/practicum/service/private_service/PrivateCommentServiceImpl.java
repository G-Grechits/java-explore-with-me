package ru.practicum.service.private_service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.State;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentShortDto;
import ru.practicum.entity.Comment;
import ru.practicum.entity.Event;
import ru.practicum.entity.Request;
import ru.practicum.entity.User;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.repository.CommentRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.CommentMapper.toComment;
import static ru.practicum.mapper.CommentMapper.toCommentDto;

@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<CommentShortDto> getByUserId(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);

        return commentRepository.findAllByCommentatorIdAndStatus(userId, State.PUBLISHED, pageable).stream()
                .map(CommentMapper::toCommentShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(long userId, long commId) {
        Comment comment = getCommentFromRepository(commId, userId);

        return toCommentDto(comment);
    }

    @Override
    public CommentDto create(long userId, long eventId, CommentDto commentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с ID = %d не найден.", userId)));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Событие с ID = %d не найдено.", eventId)));
        Request request = requestRepository.findByRequesterIdAndEventId(userId, eventId)
                .orElseThrow(() -> new BadRequestException(String.format(
                        "Пользователь с ID = %d не оставлял заявку на участие в событии с ID = %d.", userId, eventId)));
        if (!request.getStatus().equals(State.CONFIRMED)) {
            throw new BadRequestException(String.format(
                    "Пользователь с ID = %d не участвовал в событии с ID = %d.", userId, eventId));
        }
        // Для успешного прохождения тестов в Postman закомментируйте код в блоке if, расположенный ниже
        if (event.getEventDate().isAfter(LocalDateTime.now())) {
            throw new ValidationException("Нельзя комментировать непроведённое событие.");
        }
        Comment comment = commentRepository.save(toComment(commentDto, user, event));

        return toCommentDto(comment);
    }

    @Override
    public CommentDto update(long userId, long eventId, CommentDto commentDto) {
        Comment formerComment = commentRepository.findByIdAndCommentatorIdAndEventId(commentDto.getId(), userId, eventId)
                .orElseThrow(() -> new NotFoundException("Комментарий с заданными параметрами не найден."));
        if (formerComment.getStatus().equals(State.CANCELED)) {
            throw new ValidationException("Нельзя редактировать отменённый комментарий.");
        }
        formerComment.setText(commentDto.getText());
        formerComment.setStatus(State.PENDING);
        Comment comment = commentRepository.save(formerComment);

        return toCommentDto(comment);
    }

    @Override
    public void delete(long userId, long commId) {
        getCommentFromRepository(commId, userId);
        commentRepository.deleteById(commId);
    }

    private Comment getCommentFromRepository(long id, long userId) {
        return commentRepository.findByIdAndCommentatorId(id, userId)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Комментарий с ID = %d пользователя с ID = %d не найден.", id, userId)));
    }
}
