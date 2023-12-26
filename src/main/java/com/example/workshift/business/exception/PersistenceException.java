package com.example.workshift.business.exception;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class PersistenceException extends RuntimeException {
    private final ErrorType errorType;

    protected PersistenceException(@NonNull final String message, @NonNull final ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
