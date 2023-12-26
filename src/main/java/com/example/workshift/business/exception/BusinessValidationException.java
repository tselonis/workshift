package com.example.workshift.business.exception;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class BusinessValidationException extends RuntimeException {
    private final ErrorType errorType;

    protected BusinessValidationException(@NonNull final String message, @NonNull final ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
