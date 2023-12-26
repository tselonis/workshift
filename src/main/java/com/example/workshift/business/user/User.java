package com.example.workshift.business.user;

import lombok.NonNull;

public record User(
        Long id,
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull String address,
        @NonNull String phoneNumber,
        @NonNull String email
) {

    public static User of(@NonNull final CreateUserRequest createUserRequest) {
        return new User(
                null,
                createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.address(),
                createUserRequest.phoneNumber(),
                createUserRequest.email()
        );
    }
}
