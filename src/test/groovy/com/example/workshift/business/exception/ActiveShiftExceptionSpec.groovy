package com.example.workshift.business.exception

import spock.lang.Specification

import static com.example.workshift.business.exception.ErrorType.ACTIVE_SHIFT_EXISTS

class ActiveShiftExceptionSpec extends Specification {

    private static final String ERROR_MESSAGE = "error message"

    def "verify constructor null guards"() {
        when:
            new ActiveShiftException(null)
        then:
            thrown(NullPointerException)
    }

    def "verify exception info"() {
        when:
            ActiveShiftException exception = new ActiveShiftException(ERROR_MESSAGE)
        then:
            exception.getMessage() == ERROR_MESSAGE
            exception.getErrorType() == ACTIVE_SHIFT_EXISTS
    }
}
