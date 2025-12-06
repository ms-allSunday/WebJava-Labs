package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.dto.customer.CustomerDto;

public interface CustomerService {
    CustomerDto getCustomerById(Long id);
    CustomerDto createCustomer(CustomerDto customerDto);
}