package com.example.workshift.business.user

import spock.lang.Specification

import static com.example.workshift.TestDataProvider.createUserRequest

class UserServiceImplSpec extends Specification {

    UserRepository userRepository
    UserService userService

    def setup() {
        userRepository = Mock()
        userService = new UserServiceImpl(userRepository)
    }

    def "verify create user null guards"() {
        when:
            userService.createUser(null)
        then:
            thrown(NullPointerException)
    }

    def "verify create user"() {
        when:
            userService.createUser(createUserRequest())
        then:
            1 * userRepository.createUser(_ as User)
    }
}
