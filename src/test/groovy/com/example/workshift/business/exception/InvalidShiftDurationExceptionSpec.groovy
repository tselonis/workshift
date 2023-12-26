package com.example.workshift.business.exception

import spock.lang.Specification

class InvalidShiftDurationExceptionSpec extends Specification {
    private static final String ERROR_MESSAGE = "Invalid shift duration"

    def "verify exception info"() {
        when:
            InvalidShiftDurationException exception = new InvalidShiftDurationException()
        then:
            exception.getMessage() == ERROR_MESSAGE
            exception.getErrorType() == ErrorType.SHIFT_DURATION_INVALID
    }
}
