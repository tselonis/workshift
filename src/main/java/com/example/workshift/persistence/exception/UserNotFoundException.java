package com.example.workshift.persistence.exception;

import com.example.workshift.business.exception.PersistenceException;

import static com.example.workshift.business.exception.ErrorType.USER_NOT_FOUND;

public class UserNotFoundException extends PersistenceException {
    private static final String ERROR_MESSAGE = "User not found";

    public UserNotFoundException() {
        super(ERROR_MESSAGE, USER_NOT_FOUND);
    }
}
