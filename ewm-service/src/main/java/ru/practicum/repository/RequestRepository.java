package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.State;
import ru.practicum.entity.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(long requesterId);

    List<Request> findAllByEventId(long eventId);

    List<Request> findAllByEventIdAndStatus(long eventId, State status);

    Optional<Request> findByIdAndEventId(long id, long eventId);

    Optional<Request> findByRequesterIdAndEventId(long requesterId, long eventId);
}
