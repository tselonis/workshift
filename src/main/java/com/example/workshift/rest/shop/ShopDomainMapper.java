package com.example.workshift.rest.shop;

import com.example.workshift.business.shop.CreateShopRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
class ShopDomainMapper {
    CreateShopRequest mapToCreateShopRequest(@NonNull final CreateShopRequestDto createShopRequestDto) {
        return new CreateShopRequest(
                createShopRequestDto.name(),
                createShopRequestDto.cvr(),
                createShopRequestDto.address(),
                createShopRequestDto.phoneNumber(),
                createShopRequestDto.email()
        );
    }
}
