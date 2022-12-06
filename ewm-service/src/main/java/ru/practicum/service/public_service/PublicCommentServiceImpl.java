package ru.practicum.service.public_service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.State;
import ru.practicum.dto.CommentShortDto;
import ru.practicum.entity.Comment;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.CommentMapper.toCommentShortDto;

@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentShortDto> getByEventId(long eventId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);

        return commentRepository.findAllByEventIdAndStatus(eventId, State.PUBLISHED, pageable).stream()
                .map(CommentMapper::toCommentShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentShortDto getById(long commId) {
        Comment comment = commentRepository.findByIdAndStatus(commId, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException(String.format("Комментарий с ID = %d не найден.", commId)));

        return toCommentShortDto(comment);
    }
}
