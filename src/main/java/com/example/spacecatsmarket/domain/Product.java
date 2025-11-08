package com.example.spacecatsmarket.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Integer inStock;
}
