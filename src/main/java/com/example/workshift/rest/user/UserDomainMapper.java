package com.example.workshift.rest.user;

import com.example.workshift.business.user.CreateUserRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
class UserDomainMapper {
    public CreateUserRequest mapToCreateUserRequest(@NonNull final CreateUserRequestDto createUserRequestDto) {
        return new CreateUserRequest(
                createUserRequestDto.firstName(),
                createUserRequestDto.lastName(),
                createUserRequestDto.address(),
                createUserRequestDto.phoneNumber(),
                createUserRequestDto.email()
        );
    }
}
