package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.AbstractIT;
import com.example.spacecatsmarket.dto.category.CategoryDto;
import com.example.spacecatsmarket.repository.CategoryRepository;
import com.example.spacecatsmarket.repository.entity.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Category Service Integration Tests")
class CategoryServiceIT extends AbstractIT {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Create Category: Should save to DB")
    void createCategory() {
        CategoryDto dto = CategoryDto.builder().name("Space Food").build();

        CategoryDto created = categoryService.createCategory(dto);

        assertNotNull(created.getId());
        assertEquals("Space Food", created.getName());
        assertTrue(categoryRepository.existsById(created.getId()));
    }

    @Test
    @DisplayName("Get All Categories: Should return list")
    void getAllCategories() {
        categoryRepository.save(CategoryEntity.builder().name("Cat 1").build());
        categoryRepository.save(CategoryEntity.builder().name("Cat 2").build());

        List<CategoryDto> list = categoryService.getAllCategories();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Update Category: Should update name in DB")
    void updateCategory() {
        CategoryEntity saved = categoryRepository.save(CategoryEntity.builder().name("Old Name").build());

        CategoryDto updateDto = CategoryDto.builder().name("New Name").build();

        CategoryDto updated = categoryService.updateCategory(saved.getId(), updateDto);

        assertEquals("New Name", updated.getName());
    }

    @Test
    @DisplayName("Delete Category: Should remove from DB")
    void deleteCategory() {
        CategoryEntity saved = categoryRepository.save(CategoryEntity.builder().name("Delete Me").build());

        categoryService.deleteCategory(saved.getId());

        assertFalse(categoryRepository.existsById(saved.getId()));
    }
}