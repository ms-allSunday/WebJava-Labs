package com.example.spacecatsmarket.web.mapper;

import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);

    Product toProductEntity(ProductRequestDto request);

    Product toProductEntity(ProductDto dto);

    List<ProductDto> toProductList(List<Product> products);
}

