package ru.practicum.controller.public_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.service.public_service.PublicCompilationService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {
    private final PublicCompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        List<CompilationDto> compilations = compilationService.getCompilations(pinned, from, size);
        log.info("Получен список подборок событий.");
        return compilations;
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable long compId) {
        CompilationDto compilation = compilationService.getCompilationById(compId);
        log.info("Получена подборка событий '{}'.", compilation.getTitle());
        return compilation;
    }
}
