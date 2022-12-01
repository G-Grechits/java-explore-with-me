package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.State;
import ru.practicum.entity.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateBetween(
            List<Long> initiatorIds, List<State> states, List<Long> categoryIds, LocalDateTime start, LocalDateTime end,
            Pageable pageable);

    @Query("SELECT e FROM Event AS e " +
            "WHERE e.annotation like %:text% OR e.description like %:text% " +
            "  AND e.category.id in :categories " +
            "  AND e.paid = :paid " +
            "  AND e.eventDate BETWEEN :start AND :end")
    List<Event> findAllByParams(@Param("text") String text, List<Long> categories, Boolean paid, LocalDateTime start,
                                LocalDateTime end, Pageable pageable);

    List<Event> findAllByCategoryId(long categoryId);

    List<Event> findAllByInitiatorId(long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(long id, long initiatorId);

    Optional<Event> findByIdAndState(long id, State state);
}
