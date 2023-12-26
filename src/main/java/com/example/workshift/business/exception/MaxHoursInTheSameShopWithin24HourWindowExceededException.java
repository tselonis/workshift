package com.example.workshift.business.exception;

import static com.example.workshift.business.exception.ErrorType.MAX_HOURS_IN_THE_SAME_SHOP_WITHIN_24_HOUR_WINDOW_EXCEEDED;

public class MaxHoursInTheSameShopWithin24HourWindowExceededException extends BusinessValidationException {
    private static final String ERROR_MESSAGE = "Max hours in the same shop within 24 hour window exceeded";
    public MaxHoursInTheSameShopWithin24HourWindowExceededException() {
        super(ERROR_MESSAGE, MAX_HOURS_IN_THE_SAME_SHOP_WITHIN_24_HOUR_WINDOW_EXCEEDED);
    }
}
