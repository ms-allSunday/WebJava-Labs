package com.example.spacecatsmarket.service.impl;


import com.example.spacecatsmarket.dto.order.OrderDto;
import com.example.spacecatsmarket.dto.order.OrderRequestDto;
import com.example.spacecatsmarket.repository.CustomerRepository;
import com.example.spacecatsmarket.repository.OrderRepository;
import com.example.spacecatsmarket.repository.ProductRepository;
import com.example.spacecatsmarket.repository.entity.CustomerEntity;
import com.example.spacecatsmarket.repository.entity.OrderEntity;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import com.example.spacecatsmarket.service.OrderService;
import com.example.spacecatsmarket.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        return orderMapper.toDtoList(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderByNaturalId(UUID orderNumber) {
        OrderEntity entity = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDto(entity);
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderRequestDto request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<ProductEntity> products = productRepository.findAllById(request.getProducts());
        if (products.isEmpty()) {
            throw new RuntimeException("No products found for order");
        }

        OrderEntity order = OrderEntity.builder()
                .customer(customer)
                .products(products)
                .orderNumber(UUID.randomUUID())
                .build();

        OrderEntity saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}