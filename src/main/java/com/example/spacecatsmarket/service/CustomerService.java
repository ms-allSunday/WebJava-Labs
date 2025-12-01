package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.Customer;

public interface CustomerService {
    Customer getCustomerById(Long id);
    Customer createCustomer(Customer customer);
}