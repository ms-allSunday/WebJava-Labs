package com.example.spacecatsmarket.web.mapper;

import com.example.spacecatsmarket.dto.order.OrderDto;
import com.example.spacecatsmarket.repository.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    OrderDto toDto(OrderEntity entity);

    @Mapping(target = "customer", ignore = true)
    OrderEntity toEntity(OrderDto dto);

    List<OrderDto> toDtoList(List<OrderEntity> entities);
}
