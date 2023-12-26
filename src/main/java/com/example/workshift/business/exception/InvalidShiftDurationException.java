package com.example.workshift.business.exception;

import static com.example.workshift.business.exception.ErrorType.SHIFT_DURATION_INVALID;

public class InvalidShiftDurationException extends BusinessValidationException {
    private static final String ERROR_MESSAGE = "Invalid shift duration";
    public InvalidShiftDurationException() {
        super(ERROR_MESSAGE, SHIFT_DURATION_INVALID);
    }
}
