package ru.practicum.service.public_service;

import ru.practicum.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    List<CompilationDto> get(Boolean pinned, int from, int size);

    CompilationDto getById(long id);
}
