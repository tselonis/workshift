package com.example.workshift.rest.user

import com.example.workshift.business.user.CreateUserRequest
import com.example.workshift.business.user.User
import com.example.workshift.business.user.UserService
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static com.example.workshift.TestDataProvider.USER_ADDRESS
import static com.example.workshift.TestDataProvider.USER_EMAIL
import static com.example.workshift.TestDataProvider.USER_FIRST_NAME
import static com.example.workshift.TestDataProvider.USER_LAST_NAME
import static com.example.workshift.TestDataProvider.USER_PHONE_NUMBER

class UserControllerSpec extends Specification {

    UserDomainMapper userDomainMapper
    UserService userService
    UserController userController

    def setup() {
        userDomainMapper = Mock()
        userService = Mock()
        userController = new UserController(userDomainMapper, userService)
    }

    def "verify create user"() {
        given:
            CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto(USER_FIRST_NAME, USER_LAST_NAME, USER_ADDRESS, USER_PHONE_NUMBER, USER_EMAIL)
            CreateUserRequest createUserRequest = new CreateUserRequest(USER_FIRST_NAME, USER_LAST_NAME, USER_ADDRESS, USER_PHONE_NUMBER, USER_EMAIL)
            User createdUser = new User(1L, USER_FIRST_NAME, USER_LAST_NAME, USER_ADDRESS, USER_PHONE_NUMBER, USER_EMAIL)
        when:
            ResponseEntity<Long> responseEntity = userController.createUser(createUserRequestDto)
        then:
            1 * userDomainMapper.mapToCreateUserRequest(createUserRequestDto) >> createUserRequest
            1 * userService.createUser(createUserRequest) >> createdUser
            responseEntity.getBody() == createdUser.id()
    }
}
