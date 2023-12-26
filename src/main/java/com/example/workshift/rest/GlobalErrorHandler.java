package com.example.workshift.rest;

import com.example.workshift.business.exception.BusinessValidationException;
import com.example.workshift.business.exception.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.workshift.business.exception.ErrorType.SERVER_ERROR;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ErrorResponse> handleBusinessValidationException(final BusinessValidationException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), exception.getErrorType().getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ErrorResponse> handlePersistenceException(final PersistenceException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), exception.getErrorType().getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), SERVER_ERROR.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
