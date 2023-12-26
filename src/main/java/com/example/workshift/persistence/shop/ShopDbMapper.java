package com.example.workshift.persistence.shop;

import com.example.workshift.business.shop.Shop;
import org.springframework.stereotype.Component;

@Component
class ShopDbMapper {
    ShopEntity mapToShopEntityForCreate(final Shop shop) {
        final ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName(shop.name());
        shopEntity.setCvr(shop.cvr());
        shopEntity.setAddress(shop.address());
        shopEntity.setPhoneNumber(shop.phoneNumber());
        shopEntity.setEmail(shop.email());

        return shopEntity;
    }

    Shop mapToShop(final ShopEntity shopEntity) {
        return new Shop(
                shopEntity.getId(),
                shopEntity.getName(),
                shopEntity.getCvr(),
                shopEntity.getAddress(),
                shopEntity.getPhoneNumber(),
                shopEntity.getEmail()
        );
    }
}
