package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(int id);
    ProductDto createProduct(ProductRequestDto requestDto);
    ProductDto updateProduct(int id, ProductRequestDto requestDto);
    boolean deleteProduct(int id);
}
