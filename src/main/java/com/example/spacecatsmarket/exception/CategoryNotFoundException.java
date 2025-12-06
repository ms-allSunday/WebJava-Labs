package com.example.spacecatsmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with id %s not found";

    public CategoryNotFoundException(Long id) {
        super(String.format(CATEGORY_NOT_FOUND_MESSAGE, id));
    }
}
