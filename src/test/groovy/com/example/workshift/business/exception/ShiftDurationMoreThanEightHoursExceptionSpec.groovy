package com.example.workshift.business.exception

import spock.lang.Specification

class ShiftDurationMoreThanEightHoursExceptionSpec extends Specification {
    private static final String ERROR_MESSAGE = "Shift duration is more than 8 hours"

    def "verify exception info"() {
        when:
            ShiftDurationMoreThanEightHoursException exception = new ShiftDurationMoreThanEightHoursException()
        then:
            exception.getMessage() == ERROR_MESSAGE
            exception.getErrorType() == ErrorType.SHIFT_DURATION_MORE_THAN_EIGHT_HOURS
    }
}
