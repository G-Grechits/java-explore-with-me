package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.State;
import ru.practicum.entity.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateBetween(
            List<Long> initiatorIds, List<State> states, List<Long> categoryIds, LocalDateTime start, LocalDateTime end,
            Pageable pageable);

    List<Event>
    findAllByAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndCategoryIdInAndPaidAndEventDateBetween(
            String annotation, String description, List<Long> categoryIds, Boolean paid, LocalDateTime start,
            LocalDateTime end, Pageable pageable);

    List<Event> findAllByCategoryId(long categoryId);

    List<Event> findAllByInitiatorId(long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(long id, long initiatorId);

    Optional<Event> findByIdAndState(long id, State state);
}
