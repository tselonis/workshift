package com.example.workshift.rest.user

import com.example.workshift.business.user.CreateUserRequest
import spock.lang.Shared
import spock.lang.Specification

import static com.example.workshift.TestDataProvider.USER_ADDRESS
import static com.example.workshift.TestDataProvider.USER_EMAIL
import static com.example.workshift.TestDataProvider.USER_FIRST_NAME
import static com.example.workshift.TestDataProvider.USER_LAST_NAME
import static com.example.workshift.TestDataProvider.USER_PHONE_NUMBER

class UserDomainMapperSpec extends Specification {

    @Shared
    UserDomainMapper userDomainMapper = new UserDomainMapper()

    def "verify create user request mapping null guards"() {
        when:
            userDomainMapper.mapToCreateUserRequest(null)
        then:
            thrown(NullPointerException)
    }

    def "verify create user request mapping"() {
        given:
            CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto(USER_FIRST_NAME, USER_LAST_NAME, USER_ADDRESS, USER_PHONE_NUMBER, USER_EMAIL)
        when:
            CreateUserRequest createUserRequest = userDomainMapper.mapToCreateUserRequest(createUserRequestDto)
        then:
            verifyAll(createUserRequest) {
                firstName() == createUserRequestDto.firstName()
                lastName() == createUserRequestDto.lastName()
                address() == createUserRequestDto.address()
                phoneNumber() == createUserRequestDto.phoneNumber()
                email() == createUserRequestDto.email()
            }
    }
}
