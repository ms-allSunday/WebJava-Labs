package com.example.spacecatsmarket.service;


import com.example.spacecatsmarket.config.MapperTestConfig;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;
import com.example.spacecatsmarket.exception.ProductNotFoundException;
import com.example.spacecatsmarket.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ProductServiceImpl.class})
@Import(MapperTestConfig.class)
@DisplayName("ProductService Tests")
class ProductServiceTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Should return all test products")
    void getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();

        assertEquals(3, products.size(), "Expected three test products");

        assertIterableEquals(
                products.stream().map(ProductDto::getName).collect(Collectors.toList()),
                new ArrayList<>(Arrays.asList("Meteor Ball", "Comet Scratcher", "Nebula Bed"))
        );
    }

    @Test
    @DisplayName("Should return product by ID with all fields matching")
    void getProductById() {
        ProductDto product = productService.getProductById(1L);

        assertEquals("Meteor Ball", product.getName(), "Product name should match");
        assertEquals("Play ball shaped like a meteor", product.getDescription(), "Product description should match");
        assertEquals(10.0, product.getPrice(), "Product price should match");
        assertEquals(15, product.getInStock(), "Product stock should match");
    }

    @Test
    @DisplayName("Should throw exception when product id not found")
    void getProductByIdNotFound() {
        Long invalidId = 999L;
        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(invalidId),
                "Expected ProductNotFoundException for invalid id");
    }

    @Test
    @DisplayName("Should create new product")
    void createProduct() {
        ProductRequestDto requestDto = new ProductRequestDto(
                "Cosmic coffee",
                "Coffee from Mars",
                55.0,
                15
        );

        ProductDto createdProduct = productService.createProduct(requestDto);

        assertNotNull(createdProduct, "Created product should not be null");
        assertEquals("Cosmic coffee", createdProduct.getName(), "Created product name should match");
        assertEquals("Coffee from Mars", createdProduct.getDescription(), "Created product description should match");
        assertEquals(55.0, createdProduct.getPrice(), "Created product price should match");
        assertEquals(15, createdProduct.getInStock(), "Created product stock should match");
    }

    @Test
    @DisplayName("Should update product created in createProduct test")
    void updateCreatedProduct() {

        ProductDto createdProduct = productService.getProductById(1L);

        ProductRequestDto updateRequest = new ProductRequestDto(
                "Cosmic coffee",
                "Coffee from Mars",
                55.0,
                15
        );

        ProductDto updatedProduct = productService.updateProduct(createdProduct.getId(), updateRequest);

        assertEquals("Cosmic coffee", updatedProduct.getName(), "Product name should be updated");
        assertEquals("Coffee from Mars", updatedProduct.getDescription(), "Product description should be updated");
        assertEquals(55.0, updatedProduct.getPrice(), "Product price should be updated");
        assertEquals(15, updatedProduct.getInStock(), "Product stock should be updated");
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent product")
    void updateProductNotFound() {
        Long invalidId = 999L;
        ProductRequestDto updateRequest = new ProductRequestDto(
                "Cosmic coffee",
                "Coffee from Mars",
                55.0,
                15
        );

        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(invalidId, updateRequest),
                "Expected ProductNotFoundException for non-existent product ID");
    }

    @Test
    @DisplayName("Should delete product by id")
    void deleteProduct() {
        ProductDto product = productService.getProductById(1L);

        assertDoesNotThrow(() -> productService.deleteProduct(product.getId()),
                "Deleting existing product should not throw exception");

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(product.getId()),
                "Deleted product should not be found");
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent product")
    void deleteProductNotFound() {
        Long invalidId = 999L;

        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(invalidId),
                "Expected ProductNotFoundException when deleting non-existent product");
    }
}
