package com.example.workshift.business.user

import spock.lang.Specification

import static com.example.workshift.TestDataProvider.USER_ADDRESS
import static com.example.workshift.TestDataProvider.USER_EMAIL
import static com.example.workshift.TestDataProvider.USER_FIRST_NAME
import static com.example.workshift.TestDataProvider.USER_LAST_NAME
import static com.example.workshift.TestDataProvider.USER_PHONE_NUMBER

class CreateUserRequestSpec extends Specification {

    def "verify constructor null guards"() {
        when:
            new CreateUserRequest(firstName, lastName, address, phoneNumber, email)
        then:
            thrown(NullPointerException)
        where:
            firstName       | lastName       | address      | phoneNumber       | email
            null            | USER_LAST_NAME | USER_ADDRESS | USER_PHONE_NUMBER | USER_EMAIL
            USER_FIRST_NAME | null           | USER_ADDRESS | USER_PHONE_NUMBER | USER_EMAIL
            USER_FIRST_NAME | USER_LAST_NAME | null         | USER_PHONE_NUMBER | USER_EMAIL
            USER_FIRST_NAME | USER_LAST_NAME | USER_ADDRESS | null              | USER_EMAIL
            USER_FIRST_NAME | USER_LAST_NAME | USER_ADDRESS | USER_PHONE_NUMBER | null
    }
}
