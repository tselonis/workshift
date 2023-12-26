package com.example.workshift.persistence.exception;

import com.example.workshift.business.exception.PersistenceException;

import static com.example.workshift.business.exception.ErrorType.SHOP_NOT_FOUND;

public class ShopNotFoundException extends PersistenceException {
    private static final String ERROR_MESSAGE = "Shop not found";

    public ShopNotFoundException() {
        super(ERROR_MESSAGE, SHOP_NOT_FOUND);
    }
}
