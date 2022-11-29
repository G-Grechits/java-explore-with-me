package ru.practicum.service.admin_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.CategoryDto;
import ru.practicum.entity.Category;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;

import static ru.practicum.mapper.CategoryMapper.toCategory;
import static ru.practicum.mapper.CategoryMapper.toCategoryDto;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        throwIfNameExists(categoryDto.getName());
        Category category = categoryRepository.save(toCategory(categoryDto));

        return toCategoryDto(category);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        Category formerCategory = getCategoryFromRepository(categoryDto.getId());
        throwIfNameExists(categoryDto.getName());
        formerCategory.setName(categoryDto.getName());
        Category category = categoryRepository.save(formerCategory);

        return toCategoryDto(category);
    }

    @Override
    public void delete(long id) {
        getCategoryFromRepository(id);
        if (!eventRepository.findAllByCategoryId(id).isEmpty()) {
            throw new ConflictException(String.format("В категории с ID = %d есть события.", id));
        }
        categoryRepository.deleteById(id);
    }

    private void throwIfNameExists(String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new ConflictException(String.format("Категория '%s' уже существует.", name));
        }
    }

    private Category getCategoryFromRepository(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Категория с ID = %d не найдена.", id)));
    }
}
