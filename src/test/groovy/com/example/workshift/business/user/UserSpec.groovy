package com.example.workshift.business.user

import spock.lang.Specification

import static com.example.workshift.TestDataProvider.USER_ADDRESS
import static com.example.workshift.TestDataProvider.USER_EMAIL
import static com.example.workshift.TestDataProvider.USER_FIRST_NAME
import static com.example.workshift.TestDataProvider.USER_LAST_NAME
import static com.example.workshift.TestDataProvider.USER_PHONE_NUMBER
import static com.example.workshift.TestDataProvider.createUserRequest

class UserSpec extends Specification {

    def "verify constructor null guards"() {
        when:
            new User(null, firstName, lastName, address, phoneNumber, email)
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

    def "verify static factory method from create request null guards"() {
        when:
            User.of(null)
        then:
            thrown(NullPointerException)
    }

    def "verify static factory method from create request"() {
        given:
            CreateUserRequest createUserRequest = createUserRequest()
        when:
            User userInstance = User.of(createUserRequest)
        then:
            verifyAll(userInstance) {
                id() == null
                firstName() == createUserRequest.firstName()
                lastName() == createUserRequest.lastName()
                address() == createUserRequest.address()
                phoneNumber() == createUserRequest.phoneNumber()
                email() == createUserRequest.email()
            }
    }
}
