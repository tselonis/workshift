package com.example.workshift.business.shop;

import lombok.NonNull;

public record Shop(
        Long id,
        @NonNull String name,
        @NonNull String cvr,
        @NonNull String address,
        @NonNull String phoneNumber,
        @NonNull String email
) {

    public static Shop of(@NonNull final CreateShopRequest createShopRequest) {
        return new Shop(
                null,
                createShopRequest.name(),
                createShopRequest.cvr(),
                createShopRequest.address(),
                createShopRequest.phoneNumber(),
                createShopRequest.email()
        );
    }
}
