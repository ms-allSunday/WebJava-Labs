package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto createProduct(ProductRequestDto requestDto);
    ProductDto updateProduct(Long id, ProductRequestDto requestDto);
    void deleteProduct(Long id);
}
