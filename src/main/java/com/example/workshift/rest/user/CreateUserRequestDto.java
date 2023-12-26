package com.example.workshift.rest.user;

public record CreateUserRequestDto(
        String firstName,
        String lastName,
        String address,
        String phoneNumber,
        String email) {
}
