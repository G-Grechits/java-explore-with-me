package ru.practicum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {

    List<Hit> findAllByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT count(ip) FROM Hit " +
            "WHERE uri = ?1")
    Integer findHitCountByUri(String uri);

    @Query("SELECT count(DISTINCT ip) FROM Hit " +
            "WHERE uri = ?1")
    Integer findHitCountByUriWithUniqueIp(String uri);
}
