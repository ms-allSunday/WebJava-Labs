package com.example.spacecatsmarket.exception;

public class FeatureNotAvailableException extends RuntimeException {

    private static final String FEATURE_NOT_AVAILABLE_MESSAGE = "The feature '%s' is currently disabled.";

    public FeatureNotAvailableException(String featureName) {
        super(String.format(FEATURE_NOT_AVAILABLE_MESSAGE, featureName));
    }
}
