package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.AbstractIT;
import com.example.spacecatsmarket.domain.Customer;
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
        // Arrange
        Customer customer = Customer.builder()
                .name("Major Tom")
                .email("tom@space.com")
                .build();

        // Act
        Customer created = customerService.createCustomer(customer);

        // Assert
        assertNotNull(created.getId());
        assertEquals("Major Tom", created.getName());
        assertTrue(customerRepository.findByEmail("tom@space.com").isPresent());
    }

    @Test
    @DisplayName("Create Duplicate Customer: Should throw Exception")
    void createCustomer_DuplicateEmail_ShouldThrowException() {
        // Arrange
        // 1. Створюємо першого клієнта
        Customer c1 = Customer.builder().name("User 1").email("duplicate@mail.com").build();
        customerService.createCustomer(c1);

        // 2. Створюємо другого з таким самим email
        Customer c2 = Customer.builder().name("User 2").email("duplicate@mail.com").build();

        // Act & Assert
        // Очікуємо RuntimeException (або твоє кастомне виключення), бо такий email вже є
        assertThrows(RuntimeException.class, () -> customerService.createCustomer(c2));
    }

    @Test
    @DisplayName("Get Customer By ID: Should return correct customer")
    void getCustomerById() {
        // Arrange
        Customer saved = customerRepository.save(Customer.builder().name("Alice").email("a@b.com").build());

        // Act
        Customer found = customerService.getCustomerById(saved.getId());

        // Assert
        assertEquals("Alice", found.getName());
    }
}