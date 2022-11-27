package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.EndpointHit;

import java.util.List;
import java.util.Map;

@Service
public class HitClient extends BaseClient {

    @Autowired
    public HitClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public ResponseEntity<Object> getEventViews(String start, String end, List<String> uris, Boolean unique) {
        String path = "/views?start={start}&end={end}&uris={uris}&unique={unique}";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(uris.get(0));
        if (uris.size() > 1) {
            for (int i = 1; i < uris.size(); i++) {
                stringBuilder.append("&").append(uris.get(i));
            }
        }
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", stringBuilder.toString(),
                "unique", unique
        );
        return get(path, parameters);
    }

    public ResponseEntity<Object> saveStats(EndpointHit endpointHit) {
        String path = "/hit";
        return post(path, endpointHit);
    }
}
