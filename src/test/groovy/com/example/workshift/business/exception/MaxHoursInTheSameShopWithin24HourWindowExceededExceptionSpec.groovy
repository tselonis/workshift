package com.example.workshift.business.exception

import spock.lang.Specification

class MaxHoursInTheSameShopWithin24HourWindowExceededExceptionSpec extends Specification {
    private static final String ERROR_MESSAGE = "Max hours in the same shop within 24 hour window exceeded"

    def "verify exception info"() {
        when:
            MaxHoursInTheSameShopWithin24HourWindowExceededException exception = new MaxHoursInTheSameShopWithin24HourWindowExceededException()
        then:
            exception.getMessage() == ERROR_MESSAGE
            exception.getErrorType() == ErrorType.MAX_HOURS_IN_THE_SAME_SHOP_WITHIN_24_HOUR_WINDOW_EXCEEDED
    }
}
