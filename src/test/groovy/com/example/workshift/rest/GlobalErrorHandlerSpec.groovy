package com.example.workshift.rest

import com.example.workshift.business.exception.BusinessValidationException
import com.example.workshift.business.exception.PersistenceException
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

import static com.example.workshift.business.exception.ErrorType.ACTIVE_SHIFT_EXISTS
import static com.example.workshift.business.exception.ErrorType.SERVER_ERROR
import static com.example.workshift.business.exception.ErrorType.USER_NOT_FOUND

class GlobalErrorHandlerSpec extends Specification {
    private static final String ERROR_MESSAGE = "error message"

    @Shared
    GlobalErrorHandler globalErrorHandler = new GlobalErrorHandler()

    def "verify business validation exception handling"() {
        when:
            ResponseEntity<ErrorResponse> errorResponse = globalErrorHandler.handleBusinessValidationException(new BusinessValidationException(ERROR_MESSAGE, ACTIVE_SHIFT_EXISTS))
        then:
            errorResponse.getStatusCode().is4xxClientError()
            errorResponse.getBody().message() == ERROR_MESSAGE
            errorResponse.getBody().errorCode() == ACTIVE_SHIFT_EXISTS.getErrorCode()
    }

    def "verify persistence exception handling"() {
        when:
            ResponseEntity<ErrorResponse> errorResponse = globalErrorHandler.handlePersistenceException(new PersistenceException(ERROR_MESSAGE, USER_NOT_FOUND))
        then:
            errorResponse.getStatusCode().is4xxClientError()
            errorResponse.getBody().message() == ERROR_MESSAGE
            errorResponse.getBody().errorCode() == USER_NOT_FOUND.getErrorCode()
    }

    def "verify generic exception handling"() {
        when:
            ResponseEntity<ErrorResponse> errorResponse = globalErrorHandler.handleException(new Exception(ERROR_MESSAGE))
        then:
            errorResponse.getStatusCode().is5xxServerError()
            errorResponse.getBody().message() == ERROR_MESSAGE
            errorResponse.getBody().errorCode() == SERVER_ERROR.getErrorCode()
    }
}
