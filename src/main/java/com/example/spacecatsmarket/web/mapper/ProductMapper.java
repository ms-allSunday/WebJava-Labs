package com.example.spacecatsmarket.web.mapper;

import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product toProductEntity(ProductRequestDto request);

    List<ProductDto> toProductList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(ProductRequestDto request, @MappingTarget Product entity);
}

