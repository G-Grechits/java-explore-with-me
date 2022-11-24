package ru.practicum.service.adminService;

import ru.practicum.dto.CategoryDto;

public interface AdminCategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(long id);
}
