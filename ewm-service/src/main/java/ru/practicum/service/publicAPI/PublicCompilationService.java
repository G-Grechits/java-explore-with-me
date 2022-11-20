package ru.practicum.service.publicAPI;

import ru.practicum.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    List<CompilationDto> getAllCompilations(boolean pinned, int from, int size);

    CompilationDto getCompilationById(long id);
}
