package ru.practicum.service.admin_service;

import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;

public interface AdminCompilationService {

    CompilationDto create(NewCompilationDto compilationDto);

    void addEventToCompilation(long id, long eventId);

    void pin(long id);

    void unpin(long id);

    void deleteEventFromCompilation(long id, long eventId);

    void delete(long id);
}
