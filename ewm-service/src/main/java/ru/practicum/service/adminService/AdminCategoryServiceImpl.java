package ru.practicum.service.adminService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.CategoryDto;
import ru.practicum.entity.Category;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        checkName(categoryDto.getName());
        Category category = CategoryMapper.toCategory(categoryDto);
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category formerCategory = getCategoryFromRepository(categoryDto.getId());
        checkName(categoryDto.getName());
        formerCategory.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(formerCategory));
    }

    @Override
    public void deleteCategory(long id) {
        getCategoryFromRepository(id);
        if (!eventRepository.findAllByCategoryId(id).isEmpty()) {
            throw new ConflictException(String.format("В категории с ID = %d есть события.", id));
        }
        categoryRepository.deleteById(id);
    }

    private void checkName(String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new ConflictException(String.format("Категория '%s' уже существует.", name));
        }
    }

    private Category getCategoryFromRepository(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Категория с ID = %d не найдена.", id)));
    }
}
