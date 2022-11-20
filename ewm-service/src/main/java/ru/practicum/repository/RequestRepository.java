package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.State;
import ru.practicum.entity.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByEventId(Long eventId);

    List<Request> findAllByEventIdAndStatus(Long eventId, State status);

    Optional<Request> findByIdAndEventId(Long id, Long eventId);

    Optional<Request> findByRequesterIdAndEventId(Long requesterId, Long eventId);
}
