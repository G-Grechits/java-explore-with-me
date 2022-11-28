package ru.practicum.controller.public_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.service.public_service.PublicCategoryService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final PublicCategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(defaultValue = "0") int from,
                                              @RequestParam(defaultValue = "10") int size) {
        List<CategoryDto> categories = categoryService.getAllCategories(from, size);
        log.info("Получен список всех категорий.");
        return categories;
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable long catId) {
        CategoryDto category = categoryService.getCategoryById(catId);
        log.info("Получена категория '{}'.", category.getName());
        return category;
    }
}
