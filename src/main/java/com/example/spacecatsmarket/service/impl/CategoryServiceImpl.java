package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.dto.category.CategoryDto;
import com.example.spacecatsmarket.exception.CategoryNotFoundException;
import com.example.spacecatsmarket.exception.PersistenceException;
import com.example.spacecatsmarket.repository.CategoryRepository;
import com.example.spacecatsmarket.repository.entity.CategoryEntity;
import com.example.spacecatsmarket.service.CategoryService;
import com.example.spacecatsmarket.web.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        try {
            CategoryEntity entity = categoryRepository.findById(id)
                    .orElseThrow(() -> new CategoryNotFoundException(id));
            return categoryMapper.toDto(entity);
        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistenceException("Error retrieving category with id: " + id, e);
        }
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity entity = categoryMapper.toEntity(categoryDto);
        CategoryEntity saved = categoryRepository.save(entity);
        return categoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        CategoryEntity existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setName(categoryDto.getName());
        return categoryMapper.toDto(categoryRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}