package ru.practicum.controller.admin_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.service.admin_service.AdminCategoryService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService categoryService;

    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.create(categoryDto);
        log.info("Создана категория '{}'.", createdCategory.getName());

        return createdCategory;
    }

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.update(categoryDto);
        log.info("Данные категории '{}' обновлены.", updatedCategory.getName());

        return updatedCategory;
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable long catId) {
        categoryService.delete(catId);
        log.info("Категория с ID = {} удалена.", catId);
    }
}
