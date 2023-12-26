package com.example.workshift.persistence.user;

import com.example.workshift.business.user.User;
import com.example.workshift.business.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
class H2UserRepository implements UserRepository {

    private UserSpringDataRepository userSpringDataRepository;
    private UserDbMapper userDbMapper;

    @Override
    public User createUser(final User user) {
        final UserEntity userEntity = userDbMapper.mapToUserEntityForCreate(user);
        final UserEntity savedUser = userSpringDataRepository.save(userEntity);

        return userDbMapper.mapToUser(savedUser);
    }
}
