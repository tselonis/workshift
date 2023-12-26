package com.example.workshift.persistence.user;

import com.example.workshift.business.user.User;
import org.springframework.stereotype.Component;

@Component
class UserDbMapper {
    UserEntity mapToUserEntityForCreate(final User user) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.firstName());
        userEntity.setLastName(user.lastName());
        userEntity.setAddress(user.address());
        userEntity.setPhoneNumber(user.phoneNumber());
        userEntity.setEmail(user.email());

        return userEntity;
    }

    User mapToUser(final UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getAddress(),
                userEntity.getPhoneNumber(),
                userEntity.getEmail()
        );
    }
}
