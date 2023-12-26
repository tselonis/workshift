package com.example.workshift.business.exception;

import static com.example.workshift.business.exception.ErrorType.SHIFT_CREATION_IN_THE_PAST;

public class ShiftCreationInThePastException extends BusinessValidationException {
    private static final String ERROR_MESSAGE = "Shift creation is in the past";
    public ShiftCreationInThePastException() {
        super(ERROR_MESSAGE, SHIFT_CREATION_IN_THE_PAST);
    }
}
