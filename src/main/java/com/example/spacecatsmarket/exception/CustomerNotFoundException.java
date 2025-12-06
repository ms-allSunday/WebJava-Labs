package com.example.spacecatsmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Customer with id %s not found";

    public CustomerNotFoundException(Long id) {
        super(String.format(ERROR_MESSAGE, id));
    }
}
