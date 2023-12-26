package com.example.workshift.persistence.user

import com.example.workshift.business.user.User
import com.example.workshift.business.user.UserRepository
import spock.lang.Specification

import static com.example.workshift.TestDataProvider.USER_ADDRESS
import static com.example.workshift.TestDataProvider.USER_EMAIL
import static com.example.workshift.TestDataProvider.USER_FIRST_NAME
import static com.example.workshift.TestDataProvider.USER_LAST_NAME
import static com.example.workshift.TestDataProvider.USER_PHONE_NUMBER

class H2UserRepositorySpec extends Specification {

    UserSpringDataRepository userSpringDataRepository
    UserDbMapper userDbMapper
    UserRepository userRepository

    def setup() {
        userSpringDataRepository = Mock()
        userDbMapper = Mock()
        userRepository = new H2UserRepository(userSpringDataRepository, userDbMapper)
    }

    def "verify create user"() {
        given:
            User user = new User(null, USER_FIRST_NAME, USER_LAST_NAME, USER_ADDRESS, USER_PHONE_NUMBER, USER_EMAIL)
            UserEntity userEntity = new UserEntity()
            userEntity.setFirstName(USER_FIRST_NAME)
            userEntity.setLastName(USER_LAST_NAME)
            userEntity.setAddress(USER_ADDRESS)
            userEntity.setPhoneNumber(USER_PHONE_NUMBER)
            userEntity.setEmail(USER_EMAIL)
        when:
            userRepository.createUser(user)
        then:
            1 * userDbMapper.mapToUserEntityForCreate(user) >> userEntity
            1 * userSpringDataRepository.save(userEntity) >> userEntity
            1 * userDbMapper.mapToUser(userEntity)
    }
}
