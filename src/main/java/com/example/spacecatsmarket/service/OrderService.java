package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order createOrder(Order order);
    void deleteOrder(Long id);
}