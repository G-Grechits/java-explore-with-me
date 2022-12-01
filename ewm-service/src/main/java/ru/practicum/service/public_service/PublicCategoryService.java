package ru.practicum.service.public_service;

import ru.practicum.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {

    List<CategoryDto> getAll(int from, int size);

    CategoryDto getById(long id);
}
