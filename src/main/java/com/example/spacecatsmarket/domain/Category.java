package com.example.spacecatsmarket.domain;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    private Long id;
    private String name;
    private List<Product> products;
}
