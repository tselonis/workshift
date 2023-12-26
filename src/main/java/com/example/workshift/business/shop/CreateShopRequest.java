package com.example.workshift.business.shop;

import lombok.NonNull;

public record CreateShopRequest(
        @NonNull String name,
        @NonNull String cvr,
        @NonNull String address,
        @NonNull String phoneNumber,
        @NonNull String email) {
}
