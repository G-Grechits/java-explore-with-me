package ru.practicum.service.admin_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.Event;
import ru.practicum.exception.NotFoundException;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.EventStatsService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.mapper.CompilationMapper.toCompilation;
import static ru.practicum.mapper.CompilationMapper.toCompilationDto;
import static ru.practicum.mapper.EventMapper.toEventShortDto;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final EventStatsService statsService;

    @Override
    public CompilationDto create(NewCompilationDto compilationDto) {
        Set<Event> events = new HashSet<>();
        Set<EventShortDto> eventShorts = new HashSet<>();
        if (compilationDto.getEvents() != null && !compilationDto.getEvents().isEmpty()) {
            events = compilationDto.getEvents().stream()
                    .map(eventRepository::findById)
                    .map(e -> e.orElseThrow(() -> new NotFoundException("Событие не найдено.")))
                    .collect(Collectors.toSet());
            eventShorts = events.stream()
                    .map(e -> toEventShortDto(
                            e, statsService.getViews(e.getId()), statsService.getConfirmedRequests(e.getId())))
                    .collect(Collectors.toSet());
        }
        Compilation compilation = compilationRepository.save(toCompilation(compilationDto, events));

        return toCompilationDto(compilation, eventShorts);
    }

    @Override
    public void addEventToCompilation(long id, long eventId) {
        Compilation compilation = getCompilationFromRepository(id);
        Event event = getEventFromRepository(eventId);
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
    }

    @Override
    public void pin(long id) {
        Compilation compilation = getCompilationFromRepository(id);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    @Override
    public void unpin(long id) {
        Compilation compilation = getCompilationFromRepository(id);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public void deleteEventFromCompilation(long id, long eventId) {
        Compilation compilation = getCompilationFromRepository(id);
        Event event = getEventFromRepository(eventId);
        compilation.getEvents().remove(event);
        compilationRepository.save(compilation);
    }

    @Override
    public void delete(long id) {
        getCompilationFromRepository(id);
        compilationRepository.deleteById(id);
    }

    private Compilation getCompilationFromRepository(long id) {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Подборка событий с ID = %d не найдена.", id)));
    }

    private Event getEventFromRepository(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Событие с ID = %d не найдено.", id)));
    }
}
