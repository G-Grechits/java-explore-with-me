package ru.practicum;

import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.util.List;

public interface HitService {

    List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique);

    EndpointHit saveStats(EndpointHit endpointHit);
}
