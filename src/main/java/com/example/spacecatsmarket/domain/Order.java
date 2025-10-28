package com.example.spacecatsmarket.domain;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private List<Product> products;
    private double totalPrice;
}
