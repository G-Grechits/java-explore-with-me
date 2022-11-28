package ru.practicum.service.public_service;

import ru.practicum.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {

    List<CategoryDto> getAllCategories(int from, int size);

    CategoryDto getCategoryById(long id);
}
