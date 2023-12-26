package com.example.workshift.persistence.exception

import spock.lang.Specification

import static com.example.workshift.business.exception.ErrorType.SHOP_NOT_FOUND

class ShopNotFoundExceptionSpec extends Specification {
    private static final String ERROR_MESSAGE = "Shop not found"

    def "verify exception info"() {
        when:
            ShopNotFoundException exception = new ShopNotFoundException()
        then:
            exception.getMessage() == ERROR_MESSAGE
            exception.getErrorType() == SHOP_NOT_FOUND
    }
}
