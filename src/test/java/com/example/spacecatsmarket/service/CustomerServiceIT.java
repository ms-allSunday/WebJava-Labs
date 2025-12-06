package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.AbstractIT;
import com.example.spacecatsmarket.domain.Customer;
import com.example.spacecatsmarket.dto.customer.CustomerDto;
import com.example.spacecatsmarket.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Customer Service Integration Tests")
class CustomerServiceIT extends AbstractIT {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Create Customer: Should save successfully")
    void createCustomer_Success() {
        CustomerDto dto = CustomerDto.builder()
                .name("Major Tom")
                .email("tom@space.com")
                .phoneNumber("123")
                .build();

        CustomerDto created = customerService.createCustomer(dto);

        assertNotNull(created.getId());
        assertEquals("Major Tom", created.getName());
        assertTrue(customerRepository.findByEmail("tom@space.com").isPresent());
    }

    @Test
    @DisplayName("Create Duplicate Customer: Should throw Exception")
    void createCustomer_DuplicateEmail_ShouldThrowException() {

        CustomerDto c1 = CustomerDto.builder().name("User 1").email("duplicate@mail.com").build();
        customerService.createCustomer(c1);
        CustomerDto c2 = CustomerDto.builder().name("User 2").email("duplicate@mail.com").build();

        assertThrows(RuntimeException.class, () -> customerService.createCustomer(c2));
    }

    @Test
    @DisplayName("Get Customer By ID: Should return correct customer")
    void getCustomerById() {
        var saved = customerRepository.save(
                com.example.spacecatsmarket.repository.entity.CustomerEntity.builder()
                        .name("Alice").email("alice@test.com").build()
        );

        CustomerDto found = customerService.getCustomerById(saved.getId());

        assertEquals("Alice", found.getName());
    }
}