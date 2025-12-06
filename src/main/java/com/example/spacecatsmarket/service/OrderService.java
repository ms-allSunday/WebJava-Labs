package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.dto.order.OrderDto;
import com.example.spacecatsmarket.dto.order.OrderRequestDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDto> getAllOrders();
    OrderDto getOrderByNaturalId(UUID orderNumber);

    OrderDto createOrder(OrderRequestDto request);
    void deleteOrder(Long id);
}