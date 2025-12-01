package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.Order;
import com.example.spacecatsmarket.repository.OrderRepository;
import com.example.spacecatsmarket.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {

        order.setOrderNumber(java.util.UUID.randomUUID());

        if (order.getOrderDate() == null) {
            order.setOrderDate(java.time.LocalDateTime.now());
        }
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}