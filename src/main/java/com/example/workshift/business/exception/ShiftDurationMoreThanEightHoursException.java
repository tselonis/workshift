package com.example.workshift.business.exception;

import static com.example.workshift.business.exception.ErrorType.SHIFT_DURATION_MORE_THAN_EIGHT_HOURS;

public class ShiftDurationMoreThanEightHoursException extends BusinessValidationException {
    private static final String ERROR_MESSAGE = "Shift duration is more than 8 hours";
    public ShiftDurationMoreThanEightHoursException() {
        super(ERROR_MESSAGE, SHIFT_DURATION_MORE_THAN_EIGHT_HOURS);
    }
}
