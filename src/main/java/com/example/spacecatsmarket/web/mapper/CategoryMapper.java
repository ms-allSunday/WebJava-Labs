package com.example.spacecatsmarket.web.mapper;

import com.example.spacecatsmarket.dto.category.CategoryDto;
import com.example.spacecatsmarket.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(CategoryEntity entity);
    CategoryEntity toEntity(CategoryDto dto);
    List<CategoryDto> toDtoList(List<CategoryEntity> entities);
}
