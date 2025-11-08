package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.AbstractIT;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;
import com.example.spacecatsmarket.exception.ProductNotFoundException;
import com.example.spacecatsmarket.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("ProductController Integration Tests")
public class ProductControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    @DisplayName("GET /api/v1/products - Should return all products")
    void getAllProducts() throws Exception {
        ProductDto product1 = new ProductDto(1L, "Astro Tunnel", "Cat tunnel with space-themed design", 35.0, 12);
        ProductDto product2 = new ProductDto(2L, "Rocket Feeder", "Automatic cat feeder shaped like a rocket", 55.0, 6);
        List<ProductDto> products = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(product1.getName())))
                .andExpect(jsonPath("$[1].name", is(product2.getName())));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("GET /api/v1/products - Should return empty list")
    void getAllProductsEmptyList() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("GET /api/v1/products/{id} - Should return product by ID")
    void getProductById() throws Exception {
        Long productId = 10L;
        ProductDto product = new ProductDto(
                10L,
                "Cosmic Milk",
                "Milk from space",
                80.5,
                10
        );

        when(productService.getProductById(10L)).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.inStock").value(product.getInStock()));

        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    @DisplayName("GET /api/v1/products/{id} - Should return 404 if product not found")
    void getProductByIdNotFound() throws Exception {
        Long invalidId = 999L;
        when(productService.getProductById(invalidId)).thenThrow(new ProductNotFoundException(invalidId));

        mockMvc.perform(get("/api/v1/products/{id}", invalidId))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(invalidId);
    }

    @Test
    @DisplayName("POST /api/v1/products - Should create new product successfully")
    void createProduct() throws Exception {
        ProductRequestDto request = new ProductRequestDto(
                "Orbiting Cat Wand",
                "Interactive wand toy with orbiting ballsA cosmic cat toy",
                22.0,
                8
        );

        ProductDto createdProduct = new ProductDto(
                10L,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getInStock()
        );

        when(productService.createProduct(any(ProductRequestDto.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.description").value(request.getDescription()))
                .andExpect(jsonPath("$.price").value(request.getPrice()))
                .andExpect(jsonPath("$.inStock").value(request.getInStock()));

        verify(productService, times(1)).createProduct(any(ProductRequestDto.class));
    }

    @Test
    @DisplayName("POST /api/v1/products - Should return 400 Bad Request for invalid input")
    void createProductInvalidInput() throws Exception {

        ProductRequestDto invalidRequest = new ProductRequestDto(
                null,
                "",
                -10.0,
                -5
        );

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(productService, never()).createProduct(any(ProductRequestDto.class));
    }

    @Test
    @DisplayName("PUT /api/v1/products/{id} - Should update product successfully")
    void updateProduct() throws Exception {
        ProductRequestDto updateRequest = new ProductRequestDto(
                "Cosmic Milk",
                "Milk from space",
                80.5,
                10
        );

        ProductDto updatedProduct = new ProductDto(
                10L,
                updateRequest.getName(),
                updateRequest.getDescription(),
                updateRequest.getPrice(),
                updateRequest.getInStock()
        );

        when(productService.updateProduct(eq(10L), any(ProductRequestDto.class)))
                .thenReturn(updatedProduct);

        mockMvc.perform(put("/api/v1/products/{id}", 10L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value(updateRequest.getName()))
                .andExpect(jsonPath("$.description").value(updateRequest.getDescription()))
                .andExpect(jsonPath("$.price").value(updateRequest.getPrice()))
                .andExpect(jsonPath("$.inStock").value(updateRequest.getInStock()));

        verify(productService, times(1)).updateProduct(eq(10L), any(ProductRequestDto.class));
    }

    @Test
    @DisplayName("PUT /api/v1/products/{id} - Should return 400 Bad Request for invalid input")
    void updateProductInvalidInput() throws Exception {
        ProductRequestDto invalidRequest = new ProductRequestDto(
                "Invalid Name",
                "Invalid Description",
                -10.0,
                -20
        );

        mockMvc.perform(put("/api/v1/products/{id}", 10L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(productService, never()).updateProduct(eq(10L), any(ProductRequestDto.class));
    }

    @Test
    @DisplayName("PUT /api/v1/products/{id} - Should return 404 if product not found")
    void updateProductNotFound() throws Exception {
        Long invalidId = 999L;

        ProductRequestDto updateRequest = new ProductRequestDto(
                "Cosmic Milk",
                "Milk from space",
                80.5,
                10
        );

        when(productService.updateProduct(eq(invalidId), any(ProductRequestDto.class)))
                .thenThrow(new ProductNotFoundException(invalidId));

        mockMvc.perform(put("/api/v1/products/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).updateProduct(eq(invalidId), any(ProductRequestDto.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/products/{id} - Should delete product successfully")
    void deleteProduct() throws Exception {
        Long productId = 10L;
        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    @DisplayName("DELETE /api/v1/products/{id} - Should return 404 if product not found")
    void deleteProductNotFound() throws Exception {
        Long invalidId = 999L;

        doThrow(new ProductNotFoundException(invalidId))
                .when(productService).deleteProduct(invalidId);

        mockMvc.perform(delete("/api/v1/products/{id}", invalidId))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).deleteProduct(invalidId);
    }
}
