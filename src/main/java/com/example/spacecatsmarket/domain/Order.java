package com.example.spacecatsmarket.domain;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Long id;
    private UUID orderNumber;
    private double totalPrice;
    private Customer customer;
    private List<Product> products;
}