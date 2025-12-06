package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.AbstractIT;
import com.example.spacecatsmarket.domain.Customer;
import com.example.spacecatsmarket.domain.Order;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.dto.order.OrderDto;
import com.example.spacecatsmarket.dto.order.OrderRequestDto;
import com.example.spacecatsmarket.repository.CustomerRepository;
import com.example.spacecatsmarket.repository.OrderRepository;
import com.example.spacecatsmarket.repository.ProductRepository;
import com.example.spacecatsmarket.repository.entity.CustomerEntity;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order Service Integration Tests")
class OrderServiceIT extends AbstractIT {

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Create Order: Should save to DB and generate Natural ID")
    void createOrder() {
        ProductEntity p = productRepository.save(ProductEntity.builder()
                .name("Phone").description("Smart").price(100.0).inStock(5).build());

        CustomerEntity c = customerRepository.save(CustomerEntity.builder()
                .name("Tom").email("tom@space.com").build());

        OrderRequestDto request = OrderRequestDto.builder()
                .customerId(c.getId())
                .products(List.of(p.getId()))
                .build();

        OrderDto saved = orderService.createOrder(request);

        assertNotNull(saved.getId(), "Database ID must be generated");
        assertNotNull(saved.getOrderNumber(), "Natural ID (UUID) must be generated");

        assertTrue(orderRepository.existsById(saved.getId()));
    }
}