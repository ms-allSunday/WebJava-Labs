package com.example.spacecatsmarket.web.mapper;

import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductRequestDto;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    
    ProductDto toProductDto(ProductEntity productEntity);

    @Mapping(target = "id", ignore = true)
    ProductEntity toProductEntity(ProductRequestDto request);

    List<ProductDto> toProductList(List<ProductEntity> products);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(ProductRequestDto request, @MappingTarget ProductEntity entity);
}

