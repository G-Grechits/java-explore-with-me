package ru.practicum.service.public_service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.entity.Compilation;
import ru.practicum.exception.NotFoundException;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.service.EventStatsService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.mapper.CompilationMapper.toCompilationDto;
import static ru.practicum.mapper.EventMapper.toEventShortDto;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventStatsService statsService;

    @Override
    public List<CompilationDto> get(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (pinned == null) {
            return compilationRepository.findAll(pageable).stream()
                    .map(c -> toCompilationDto(c, getEventsForCompilation(c)))
                    .collect(Collectors.toList());
        }
        return compilationRepository.findAllByPinned(pinned, pageable).stream()
                .map(c -> toCompilationDto(c, getEventsForCompilation(c)))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getById(long id) {
        Compilation compilation = compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Подборка событий с ID = %d не найдена.", id)));

        return toCompilationDto(compilation, getEventsForCompilation(compilation));
    }

    private Set<EventShortDto> getEventsForCompilation(Compilation compilation) {
        Set<EventShortDto> events = new HashSet<>();
        if (!compilation.getEvents().isEmpty()) {
            events = compilation.getEvents().stream()
                    .map(e -> toEventShortDto(e, statsService.getViews(e.getId()),
                            statsService.getConfirmedRequests(e.getId())))
                    .collect(Collectors.toSet());
        }
        return events;
    }
}
