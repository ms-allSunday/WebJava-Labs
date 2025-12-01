package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.AbstractIT;
import com.example.spacecatsmarket.domain.Category;
import com.example.spacecatsmarket.repository.CategoryRepository;
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
        // Arrange
        Category category = new Category();
        category.setName("Space Food");

        // Act
        Category created = categoryService.createCategory(category);

        // Assert
        assertNotNull(created.getId());
        assertEquals("Space Food", created.getName());
        // Перевіряємо, що запис дійсно є в базі
        assertTrue(categoryRepository.existsById(created.getId()));
    }

    @Test
    @DisplayName("Get All Categories: Should return list")
    void getAllCategories() {
        // Arrange
        categoryRepository.save(Category.builder().name("Cat 1").build());
        categoryRepository.save(Category.builder().name("Cat 2").build());

        // Act
        List<Category> list = categoryService.getAllCategories();

        // Assert
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Update Category: Should update name in DB")
    void updateCategory() {
        // Arrange
        Category created = categoryService.createCategory(Category.builder().name("Old Name").build());

        Category updateInfo = new Category();
        updateInfo.setName("New Name");

        // Act
        Category updated = categoryService.updateCategory(created.getId(), updateInfo);

        // Assert
        assertEquals("New Name", updated.getName());

        // Перевірка в базі
        Category inDb = categoryRepository.findById(created.getId()).orElseThrow();
        assertEquals("New Name", inDb.getName());
    }

    @Test
    @DisplayName("Delete Category: Should remove from DB")
    void deleteCategory() {
        // Arrange
        Category created = categoryService.createCategory(Category.builder().name("Delete Me").build());

        // Act
        categoryService.deleteCategory(created.getId());

        // Assert
        assertFalse(categoryRepository.existsById(created.getId()));
        assertThrows(RuntimeException.class,
                () -> categoryService.getCategoryById(created.getId()));
    }
}