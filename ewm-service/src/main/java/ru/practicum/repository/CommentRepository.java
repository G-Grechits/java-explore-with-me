package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.State;
import ru.practicum.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByCommentatorIdAndStatus(long commentatorId, State status, Pageable pageable);

    List<Comment> findAllByEventIdAndStatus(long eventId, State status, Pageable pageable);

    List<Comment> findAllByCommentatorIdInAndEventIdInAndStatusInAndCreatedBetween(
            List<Long> commentatorIds, List<Long> eventIds, List<State> statuses, LocalDateTime start, LocalDateTime end,
            Pageable pageable);

    Optional<Comment> findByIdAndCommentatorId(long id, long commentatorId);

    Optional<Comment> findByIdAndEventId(long id, long eventId);

    Optional<Comment> findByIdAndCommentatorIdAndEventId(long id, long commentatorId, long eventId);
}
