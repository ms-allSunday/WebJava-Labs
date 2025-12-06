package com.example.spacecatsmarket.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_seq")
    @SequenceGenerator(name = "customers_seq", sequenceName = "customers_seq", allocationSize = 50)
    private Long id;

    private String name;
    private String email;
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;
}
