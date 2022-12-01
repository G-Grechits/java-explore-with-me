package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.EventViews;
import ru.practicum.dto.ViewStats;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class HitController {
    private final HitService hitService;

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam String start, @RequestParam String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        List<ViewStats> viewStats = hitService.getStats(start, end, uris, unique);
        log.info("Получена статистика по посещениям.");

        return viewStats;
    }

    @PostMapping("/hit")
    public EndpointHit saveStats(@RequestBody EndpointHit endpointHit) {
        EndpointHit savedEndpointHit = hitService.saveStats(endpointHit);
        log.info("Информация о запросе к эндпоинту '{}' сохранена.", savedEndpointHit.getUri());

        return savedEndpointHit;
    }

    @GetMapping("/views")
    public EventViews getEventViews(@RequestParam String start, @RequestParam String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        EventViews eventViews = hitService.getEventViews(start, end, uris, unique);
        log.info("Получена статистика по просмотрам событий.");

        return eventViews;
    }
}
