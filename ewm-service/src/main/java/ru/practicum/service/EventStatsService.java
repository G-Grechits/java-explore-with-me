package ru.practicum.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.practicum.State;
import ru.practicum.client.HitClient;
import ru.practicum.dto.EventViews;
import ru.practicum.dto.ViewStats;
import ru.practicum.entity.Request;
import ru.practicum.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.mapper.DateTimeMapper.toTextDateTime;

@Component
@RequiredArgsConstructor
public class EventStatsService {
    private final HitClient client;
    private final RequestRepository repository;
    private final Gson gson = new Gson();

    public Integer getViews(long id) {
        int views = 0;
        String uri = "/events/" + id;
        ResponseEntity<Object> responseEntity = client.getEventViews(toTextDateTime(LocalDateTime.now().minusYears(1)),
                toTextDateTime(LocalDateTime.now()), List.of(uri), false);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String response = gson.toJson(responseEntity.getBody());
            EventViews eventViews = gson.fromJson(response, EventViews.class);
            List<ViewStats> viewStats = eventViews.getViews();
            if (!viewStats.isEmpty()) {
                views = viewStats.stream().findAny().orElseThrow().getHits();
            }
        }
        return views;
    }

    public Integer getConfirmedRequests(long id) {
        int confirmedRequests = 0;
        List<Request> requests = repository.findAllByEventIdAndStatus(id, State.CONFIRMED);
        if (!requests.isEmpty()) {
            confirmedRequests = requests.size();
        }
        return confirmedRequests;
    }
}
