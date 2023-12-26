package com.example.workshift.business.user;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User createUser(@NonNull final CreateUserRequest createUserRequest) {
        return userRepository.createUser(User.of(createUserRequest));
    }
}
