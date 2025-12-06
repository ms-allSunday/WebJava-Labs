package com.example.spacecatsmarket.web.mapper;

import com.example.spacecatsmarket.dto.customer.CustomerDto;
import com.example.spacecatsmarket.repository.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(CustomerEntity entity);

    @Mapping(target = "orders", ignore = true)
    CustomerEntity toEntity(CustomerDto dto);

    List<CustomerDto> toDtoList(List<CustomerEntity> entities);
}
