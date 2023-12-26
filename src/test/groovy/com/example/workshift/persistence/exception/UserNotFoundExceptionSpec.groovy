package com.example.workshift.persistence.exception

import spock.lang.Specification

import static com.example.workshift.business.exception.ErrorType.USER_NOT_FOUND

class UserNotFoundExceptionSpec extends Specification {
    private static final String ERROR_MESSAGE = "User not found"

    def "verify exception info"() {
        when:
            UserNotFoundException exception = new UserNotFoundException()
        then:
            exception.getMessage() == ERROR_MESSAGE
            exception.getErrorType() == USER_NOT_FOUND
    }
}
