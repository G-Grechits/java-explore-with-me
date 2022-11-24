package ru.practicum.service.adminService;

import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;

public interface AdminCompilationService {

    CompilationDto createCompilation(NewCompilationDto compilationDto);

    void addEventToCompilation(long id, long eventId);

    void pinCompilation(long id);

    void unpinCompilation(long id);

    void deleteEventFromCompilation(long id, long eventId);

    void deleteCompilation(long id);
}
