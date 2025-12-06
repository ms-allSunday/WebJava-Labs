package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.AbstractIT;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;
import com.example.spacecatsmarket.exception.ProductNotFoundException;
import com.example.spacecatsmarket.repository.ProductRepository;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Service Integration Tests")
class ProductServiceIT extends AbstractIT {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Create Product: Should save to DB without category")
    void createProduct() {
        ProductRequestDto request = new ProductRequestDto(
                "Space Milk",
                "Tasty",
                50.0,
                10
        );

        ProductDto created = productService.createProduct(request);

        assertNotNull(created.getId());
        assertEquals("Space Milk", created.getName());
        assertEquals(50.0, created.getPrice());

        assertTrue(productRepository.existsById(created.getId()));
    }

    @Test
    @DisplayName("Get All: Should return products from DB")
    void getAllProducts() {
        productRepository.save(ProductEntity.builder()
                .name("Ball")
                .description("Desc")
                .price(10.0)
                .inStock(5)
                .build());

        productRepository.save(ProductEntity.builder()
                .name("Toy")
                .description("Desc")
                .price(20.0)
                .inStock(2)
                .build());

        List<ProductDto> list = productService.getAllProducts();

        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Update Product: Should update DB record")
    void updateProduct() {
        ProductEntity p = productRepository.save(ProductEntity.builder()
                .name("Old Name")
                .description("Desc")
                .price(10.0)
                .inStock(10)
                .build());

        ProductRequestDto updateReq = new ProductRequestDto(
                "New Name",
                "New Desc",
                15.0,
                5
        );

        ProductDto updated = productService.updateProduct(p.getId(), updateReq);

        assertEquals("New Name", updated.getName());
        assertEquals(15.0, updated.getPrice());

        ProductEntity inDb = productRepository.findById(p.getId()).orElseThrow();
        assertEquals("New Name", inDb.getName());
    }

    @Test
    @DisplayName("Delete Product: Should remove from DB")
    void deleteProduct() {
        ProductEntity p = productRepository.save(ProductEntity.builder()
                .name("To Delete")
                .description("Desc")
                .price(10.0)
                .inStock(5)
                .build());

        productService.deleteProduct(p.getId());

        assertFalse(productRepository.existsById(p.getId()));
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(p.getId()));
    }
}