package com.example.workshift.business.exception

import spock.lang.Specification

class ShiftCreationInThePastExceptionSpec extends Specification {
    private static final String ERROR_MESSAGE = "Shift creation is in the past"

    def "verify exception info"() {
        when:
            ShiftCreationInThePastException exception = new ShiftCreationInThePastException()
        then:
            exception.getMessage() == ERROR_MESSAGE
            exception.getErrorType() == ErrorType.SHIFT_CREATION_IN_THE_PAST
    }
}
