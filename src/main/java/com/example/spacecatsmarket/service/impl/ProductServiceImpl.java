package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;
import com.example.spacecatsmarket.exception.ProductNotFoundException;
import com.example.spacecatsmarket.service.ProductService;
import com.example.spacecatsmarket.web.mapper.ProductMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final Map<Long, Product> mockDb = new HashMap<>();
    private final AtomicLong productId = new AtomicLong(1L);

    @PostConstruct
    public void init() {
        createMockProduct("Meteor Ball", "Play ball shaped like a meteor", 10.0, 15);
        createMockProduct("Comet Scratcher", "Claw scratcher shaped like a comet", 40.0, 8);
        createMockProduct("Nebula Bed", "Soft bed with nebula pattern", 60.0, 4);
    }

    private void createMockProduct(String name, String description, double price, int inStock) {
        Product product = new Product();
        product.setId(productId.getAndIncrement());
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setInStock(inStock);

        mockDb.put(product.getId(), product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.toProductList(new ArrayList<>(mockDb.values()));
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = mockDb.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return productMapper.toProductDto(product);
    }

    @Override
    public ProductDto createProduct(ProductRequestDto requestDto) {
        Product product = productMapper.toProductEntity(requestDto);
        product.setId(productId.getAndIncrement());
        mockDb.put(product.getId(), product);
        return productMapper.toProductDto(product);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductRequestDto requestDto) {
        Product existing = mockDb.get(id);
        if (existing == null) {
            throw new ProductNotFoundException(id);
        }

        productMapper.updateFromDto(requestDto, existing);

        mockDb.put(id, existing);
        return productMapper.toProductDto(existing);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!mockDb.containsKey(id)) {
            throw new ProductNotFoundException(id);
        }
        mockDb.remove(id);
    }
}

