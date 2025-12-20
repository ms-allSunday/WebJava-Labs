package com.example.spacecatsmarket.dto.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {

    private final List<String> cosmicWords = Arrays.asList("star", "cosmic", "galaxy", "comet", "planet", "moon", "asteroid", "nebula", "orbit", "meteor");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return false;
        return cosmicWords.stream().anyMatch(word -> value.toLowerCase().contains(word));
    }
}
