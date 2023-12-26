package com.example.workshift.business.exception

import spock.lang.Specification

class MoreThanFiveDaysInARowInTheSameShopExceptionSpec extends Specification {
    private static final String ERROR_MESSAGE = "More than five days in a row in the same shop"

    def "verify exception info"() {
        when:
            MoreThanFiveDaysInARowInTheSameShopException exception = new MoreThanFiveDaysInARowInTheSameShopException()
        then:
            exception.getMessage() == ERROR_MESSAGE
            exception.getErrorType() == ErrorType.MORE_THAN_FIVE_DAYS_IN_A_ROW_IN_THE_SAME_SHOP
    }
}
