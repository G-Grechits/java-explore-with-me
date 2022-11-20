package ru.practicum.mapper;

import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.entity.Compilation;

import java.util.HashSet;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto compilationDto) {
        return new Compilation(null, compilationDto.getTitle(),
                compilationDto.getPinned() != null ? compilationDto.getPinned() : false, new HashSet<>());
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(), compilation.getTitle(), compilation.getPinned(),
                compilation.getEvents().stream()
                        .map(EventMapper::toEventShortDto)
                        .collect(Collectors.toSet()));
    }
}
