package com.example.workshift.business.shift

import spock.lang.Specification

import java.time.Instant

import static com.example.workshift.TestDataProvider.SHOP_ID
import static com.example.workshift.TestDataProvider.USER_ID

class CreateShiftRequestSpec extends Specification {

    def "verify constructor null guards"() {
        when:
            new CreateShiftRequest(activeFrom, activeTo, userId, shopId)
        then:
            thrown(NullPointerException)
        where:
            activeFrom  | activeTo    | userId  | shopId
            null        | Instant.MAX | USER_ID | SHOP_ID
            Instant.MIN | null        | USER_ID | SHOP_ID
            Instant.MIN | Instant.MAX | null    | SHOP_ID
            Instant.MIN | Instant.MAX | USER_ID | null
    }
}
