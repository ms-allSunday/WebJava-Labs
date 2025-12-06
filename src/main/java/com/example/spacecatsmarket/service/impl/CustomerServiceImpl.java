package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.dto.customer.CustomerDto;
import com.example.spacecatsmarket.exception.CustomerAlreadyExistsException;
import com.example.spacecatsmarket.exception.CustomerNotFoundException;
import com.example.spacecatsmarket.exception.PersistenceException;
import com.example.spacecatsmarket.repository.CustomerRepository;
import com.example.spacecatsmarket.repository.entity.CustomerEntity;
import com.example.spacecatsmarket.service.CustomerService;
import com.example.spacecatsmarket.web.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerById(Long id) {
        try {
            CustomerEntity entity = customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException(id));
            return customerMapper.toDto(entity);

        } catch (CustomerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistenceException("Error fetching customer", e);
        }
    }

    @Override
    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        try {
            if (customerRepository.findByEmail(customerDto.getEmail()).isPresent()) {
                throw new CustomerAlreadyExistsException(customerDto.getEmail());
            }

            CustomerEntity entity = customerMapper.toEntity(customerDto);
            return customerMapper.toDto(customerRepository.save(entity));

        } catch (CustomerAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistenceException("Error creating customer", e);
        }
    }
}