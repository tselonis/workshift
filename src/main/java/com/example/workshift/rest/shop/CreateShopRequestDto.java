package com.example.workshift.rest.shop;

public record CreateShopRequestDto(
        String name,
        String cvr,
        String address,
        String phoneNumber,
        String email) {
}
