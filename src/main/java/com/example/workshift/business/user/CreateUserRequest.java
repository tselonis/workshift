package com.example.workshift.business.user;

import lombok.NonNull;

public record CreateUserRequest(
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull String address,
        @NonNull String phoneNumber,
        @NonNull String email) {
}
