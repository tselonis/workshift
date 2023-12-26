package com.example.workshift.business.shop

import spock.lang.Specification

import static com.example.workshift.TestDataProvider.CVR1
import static com.example.workshift.TestDataProvider.SHOP1_ADDRESS
import static com.example.workshift.TestDataProvider.SHOP1_EMAIL
import static com.example.workshift.TestDataProvider.SHOP1_NAME
import static com.example.workshift.TestDataProvider.SHOP1_PHONE_NUMBER
import static com.example.workshift.TestDataProvider.createShopRequest1

class ShopSpec extends Specification {

    def "verify constructor null guards"() {
        when:
            new Shop(null, name, cvr, address, phoneNumber, email)
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

    def "verify static factory method from create request null guards"() {
        when:
            Shop.of(null)
        then:
            thrown(NullPointerException)
    }

    def "verify static factory method from create request"() {
        given:
            CreateShopRequest createShopRequest = createShopRequest1()
        when:
            Shop shopInstance = Shop.of(createShopRequest)
        then:
            verifyAll(shopInstance) {
                id() == null
                name() == createShopRequest.name()
                cvr() == createShopRequest.cvr()
                address() == createShopRequest.address()
                phoneNumber() == createShopRequest.phoneNumber()
                email() == createShopRequest.email()
            }
    }
}
