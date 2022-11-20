package ru.practicum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {

    List<Hit> findAllByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select count(ip) from Hit where uri = ?1")
    Integer findHitCountByUri(String uri);

    @Query("select count(distinct ip) from Hit where uri = ?1")
    Integer findHitCountByUriWithUniqueIp(String uri);
}
