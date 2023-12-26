package com.example.workshift.business.exception;

import static com.example.workshift.business.exception.ErrorType.MORE_THAN_FIVE_DAYS_IN_A_ROW_IN_THE_SAME_SHOP;

public class MoreThanFiveDaysInARowInTheSameShopException extends BusinessValidationException {
    private static final String ERROR_MESSAGE = "More than five days in a row in the same shop";
    public MoreThanFiveDaysInARowInTheSameShopException() {
        super(ERROR_MESSAGE, MORE_THAN_FIVE_DAYS_IN_A_ROW_IN_THE_SAME_SHOP);
    }
}
