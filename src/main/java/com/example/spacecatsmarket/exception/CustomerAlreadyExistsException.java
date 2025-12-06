package com.example.spacecatsmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerAlreadyExistsException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Customer with email %s already exists";

    public CustomerAlreadyExistsException(String email) {
        super(String.format(ERROR_MESSAGE, email));
    }
}
