package com.example.workshift.business.exception;

import lombok.NonNull;

import static com.example.workshift.business.exception.ErrorType.ACTIVE_SHIFT_EXISTS;

public class ActiveShiftException extends BusinessValidationException {
    public ActiveShiftException(@NonNull final String errorMessage) {
        super(errorMessage, ACTIVE_SHIFT_EXISTS);
    }
}
