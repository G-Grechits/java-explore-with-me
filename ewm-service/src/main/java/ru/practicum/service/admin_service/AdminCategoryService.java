package ru.practicum.service.admin_service;

import ru.practicum.dto.CategoryDto;

public interface AdminCategoryService {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    void delete(long id);
}
