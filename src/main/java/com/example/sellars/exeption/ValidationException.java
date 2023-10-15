package com.example.sellars.exeption;


public class ValidationException extends RuntimeException {
    public ValidationException(final String message) {
        super(message);
    }
}
