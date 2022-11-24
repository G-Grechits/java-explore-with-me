package ru.practicum.mapper;

import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.Event;

import java.util.Set;

public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto compilationDto, Set<Event> events) {
        return new Compilation(null, compilationDto.getTitle(),
                compilationDto.getPinned() != null ? compilationDto.getPinned() : false, events);
    }

    public static CompilationDto toCompilationDto(Compilation compilation, Set<EventShortDto> events) {
        return new CompilationDto(compilation.getId(), compilation.getTitle(), compilation.getPinned(), events);
    }
}
