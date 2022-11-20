package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.mapper.DateTimeMapper;
import ru.practicum.mapper.HitMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;

    @Override
    public List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique) {
        List<Hit> hits = hitRepository.findAllByTimestampBetweenAndUriIn(DateTimeMapper.toLocalDateTime(start),
                DateTimeMapper.toLocalDateTime(end), uris);
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
        Hit hit = HitMapper.toHit(endpointHit);
        return HitMapper.toEndpointHit(hitRepository.save(hit));
    }
}
