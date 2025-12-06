package com.example.spacecatsmarket.domain;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private List<Order> orders;
}
