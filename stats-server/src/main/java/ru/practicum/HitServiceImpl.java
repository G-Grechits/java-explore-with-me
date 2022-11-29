package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.EventViews;
import ru.practicum.dto.ViewStats;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.mapper.DateTimeMapper.toLocalDateTime;
import static ru.practicum.mapper.HitMapper.toEndpointHit;
import static ru.practicum.mapper.HitMapper.toHit;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;

    @Override
    public List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique) {
        List<Hit> hits = hitRepository.findAllByTimestampBetweenAndUriIn(toLocalDateTime(start), toLocalDateTime(end), uris);
        List<ViewStats> viewStats = new ArrayList<>();
        for (Hit hit : hits) {
            Integer hitCount;
            if (unique) {
                hitCount = hitRepository.findHitCountByUriWithUniqueIp(hit.getUri());
            } else {
                hitCount = hitRepository.findHitCountByUri(hit.getUri());
            }
            viewStats.add(new ViewStats(hit.getApp(), hit.getUri(), hitCount));
        }
        return viewStats;
    }

    @Override
    public EndpointHit saveStats(EndpointHit endpointHit) {
        Hit hit = hitRepository.save(toHit(endpointHit));

        return toEndpointHit(hit);
    }

    @Override
    public EventViews getEventViews(String start, String end, List<String> uris, Boolean unique) {
        List<ViewStats> viewStats = getStats(start, end, uris, unique);

        return new EventViews(viewStats);
    }
}
