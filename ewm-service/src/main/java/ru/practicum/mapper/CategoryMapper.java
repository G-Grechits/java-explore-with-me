package ru.practicum.mapper;

import ru.practicum.dto.CategoryDto;
import ru.practicum.entity.Category;

public class CategoryMapper {

    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
