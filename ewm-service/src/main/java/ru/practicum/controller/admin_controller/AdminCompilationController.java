package ru.practicum.controller.admin_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.service.admin_service.AdminCompilationService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService compilationService;

    @PostMapping
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        CompilationDto createdCompilation = compilationService.createCompilation(compilationDto);
        log.info("Добавлена подборка '{}'.", createdCompilation.getTitle());
        return createdCompilation;
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable long compId, @PathVariable long eventId) {
        compilationService.addEventToCompilation(compId, eventId);
        log.info("Событие с ID = {} добавлено в подборку с ID = {}.", eventId, compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable long compId) {
        compilationService.pinCompilation(compId);
        log.info("Подборка событий с ID = {} закреплена на главной странице.", compId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable long compId) {
        compilationService.unpinCompilation(compId);
        log.info("Подборка событий с ID = {} откреплена на главной странице.", compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable long compId, @PathVariable long eventId) {
        compilationService.deleteEventFromCompilation(compId, eventId);
        log.info("Событие с ID = {} удалено из подборки с ID = {}.", eventId, compId);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        compilationService.deleteCompilation(compId);
        log.info("Подборка событий с ID = {} удалена.", compId);
    }
}
