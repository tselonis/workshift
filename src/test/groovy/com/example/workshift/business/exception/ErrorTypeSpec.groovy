package com.example.workshift.business.exception

import spock.lang.Specification

class ErrorTypeSpec extends Specification {

    def "verify type count"() {
        expect:
            ErrorType.values().size() == 9
    }

    def "verify error codes"() {
        expect:
            ErrorType.SHIFT_DURATION_INVALID.getErrorCode() == "SHIFT_DURATION_INVALID"
            ErrorType.SHIFT_DURATION_MORE_THAN_EIGHT_HOURS.getErrorCode() == "SHIFT_DURATION_MORE_THAN_EIGHT_HOURS"
            ErrorType.SHIFT_CREATION_IN_THE_PAST.getErrorCode() == "SHIFT_CREATION_IN_THE_PAST"
            ErrorType.MAX_HOURS_IN_THE_SAME_SHOP_WITHIN_24_HOUR_WINDOW_EXCEEDED.getErrorCode() == "MAX_HOURS_IN_THE_SAME_SHOP_WITHIN_24_HOUR_WINDOW_EXCEEDED"
            ErrorType.MORE_THAN_FIVE_DAYS_IN_A_ROW_IN_THE_SAME_SHOP.getErrorCode() == "MORE_THAN_FIVE_DAYS_IN_A_ROW_IN_THE_SAME_SHOP"
            ErrorType.ACTIVE_SHIFT_EXISTS.getErrorCode() == "ACTIVE_SHIFT_EXISTS"
            ErrorType.USER_NOT_FOUND.getErrorCode() == "USER_NOT_FOUND"
            ErrorType.SHOP_NOT_FOUND.getErrorCode() == "SHOP_NOT_FOUND"
            ErrorType.SERVER_ERROR.getErrorCode() == "SERVER_ERROR"
    }
}
