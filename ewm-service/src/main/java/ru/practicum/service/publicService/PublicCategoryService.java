package ru.practicum.service.publicService;

import ru.practicum.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {

    List<CategoryDto> getAllCategories(int from, int size);

    CategoryDto getCategoryById(long id);
}
