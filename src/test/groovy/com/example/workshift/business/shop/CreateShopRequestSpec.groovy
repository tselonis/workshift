package com.example.workshift.business.shop


import spock.lang.Specification

import static com.example.workshift.TestDataProvider.CVR1
import static com.example.workshift.TestDataProvider.SHOP1_ADDRESS
import static com.example.workshift.TestDataProvider.SHOP1_EMAIL
import static com.example.workshift.TestDataProvider.SHOP1_NAME
import static com.example.workshift.TestDataProvider.SHOP1_PHONE_NUMBER

class CreateShopRequestSpec extends Specification {

    def "verify constructor null guards"() {
        when:
            new CreateShopRequest(name, cvr, address, phoneNumber, email)
        then:
            thrown(NullPointerException)
        where:
            name       | cvr  | address       | phoneNumber        | email
            null       | CVR1 | SHOP1_ADDRESS | SHOP1_PHONE_NUMBER | SHOP1_EMAIL
            SHOP1_NAME | null | SHOP1_ADDRESS | SHOP1_PHONE_NUMBER | SHOP1_EMAIL
            SHOP1_NAME | CVR1 | null          | SHOP1_PHONE_NUMBER | SHOP1_EMAIL
            SHOP1_NAME | CVR1 | SHOP1_ADDRESS | null               | SHOP1_EMAIL
            SHOP1_NAME | CVR1 | SHOP1_ADDRESS | SHOP1_PHONE_NUMBER | null
    }
}
