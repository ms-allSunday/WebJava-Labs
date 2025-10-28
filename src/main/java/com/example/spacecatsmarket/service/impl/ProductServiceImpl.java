package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;
import com.example.spacecatsmarket.exception.ProductNotFoundException;
import com.example.spacecatsmarket.service.ProductService;
import com.example.spacecatsmarket.web.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final Map<Integer, Product> mockDb = new HashMap<>();
    private int currentId = 1;

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.toProductList(new ArrayList<>(mockDb.values()));
    }

    @Override
    public ProductDto getProductById(int id) {
        Product product = mockDb.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return productMapper.toProductDto(product);
    }

    @Override
    public ProductDto createProduct(ProductRequestDto requestDto) {
        Product product = productMapper.toProductEntity(requestDto);
        product.setId(currentId++);
        mockDb.put(product.getId(), product);
        return productMapper.toProductDto(product);
    }

    @Override
    public ProductDto updateProduct(int id, ProductRequestDto requestDto) {
        Product existing = mockDb.get(id);
        if (existing == null) {
            throw new NoSuchElementException("Product not found with id: " + id);
        }

        existing.setName(requestDto.getName());
        existing.setDescription(requestDto.getDescription());
        existing.setPrice(requestDto.getPrice());
        existing.setInStock(requestDto.getInStock());

        mockDb.put(id, existing);
        return productMapper.toProductDto(existing);
    }

    @Override
    public boolean deleteProduct(int id) {
        if (!mockDb.containsKey(id)) {
            return false;
        }
        mockDb.remove(id);
        return true;
    }
}

