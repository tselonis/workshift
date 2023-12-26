package com.example.workshift.persistence.user

import com.example.workshift.business.user.User
import spock.lang.Shared
import spock.lang.Specification

import static com.example.workshift.TestDataProvider.USER_ADDRESS
import static com.example.workshift.TestDataProvider.USER_EMAIL
import static com.example.workshift.TestDataProvider.USER_FIRST_NAME
import static com.example.workshift.TestDataProvider.USER_LAST_NAME
import static com.example.workshift.TestDataProvider.USER_PHONE_NUMBER
import static com.example.workshift.TestDataProvider.createUserRequest

class UserDbMapperSpec extends Specification {

    @Shared
    UserDbMapper userDbMapper = new UserDbMapper()

    def "verify domain to user entity mapping for create"() {
        given:
            User user = User.of(createUserRequest())
        when:
            UserEntity userEntity = userDbMapper.mapToUserEntityForCreate(user)
        then:
            userEntity.getId() == null
            userEntity.getVersion() == null
            userEntity.getFirstName() == user.firstName()
            userEntity.getLastName() == user.lastName()
            userEntity.getAddress() == user.address()
            userEntity.getPhoneNumber() == user.phoneNumber()
            userEntity.getEmail() == user.email()
    }

    def "verify user entity to domain mapping"() {
        given:
            UserEntity userEntity = new UserEntity()
            userEntity.setId(1L)
            userEntity.setVersion(1L)
            userEntity.setFirstName(USER_FIRST_NAME)
            userEntity.setLastName(USER_LAST_NAME)
            userEntity.setAddress(USER_ADDRESS)
            userEntity.setPhoneNumber(USER_PHONE_NUMBER)
            userEntity.setEmail(USER_EMAIL)
        when:
            User user = userDbMapper.mapToUser(userEntity)
        then:
            user.id() == userEntity.getId()
            user.firstName() == userEntity.getFirstName()
            user.lastName() == userEntity.getLastName()
            user.address() == userEntity.getAddress()
            user.phoneNumber() == userEntity.getPhoneNumber()
            user.email() == userEntity.getEmail()
    }
}
