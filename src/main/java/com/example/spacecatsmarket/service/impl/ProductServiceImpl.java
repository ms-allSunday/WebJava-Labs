package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;
import com.example.spacecatsmarket.exception.ProductNotFoundException;
import com.example.spacecatsmarket.repository.ProductRepository;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import com.example.spacecatsmarket.repository.projection.ProductProjection;
import com.example.spacecatsmarket.service.ProductService;
import com.example.spacecatsmarket.web.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productMapper.toProductList(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toProductDto(product);
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductRequestDto requestDto) {
        ProductEntity product = productMapper.toProductEntity(requestDto);
        ProductEntity savedProduct = productRepository.save(product);
        return productMapper.toProductDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductRequestDto requestDto) {
        ProductEntity existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productMapper.updateFromDto(requestDto, existing);

        ProductEntity savedProduct = productRepository.save(existing);
        return productMapper.toProductDto(savedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductProjection> getProductsExpensiveThan(double minPrice) {
        return productRepository.findProductsByPriceGreaterThan(minPrice);
    }
}