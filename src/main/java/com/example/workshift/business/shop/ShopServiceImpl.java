package com.example.workshift.business.shop;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
class ShopServiceImpl implements ShopService {

    private ShopRepository shopRepository;

    @Override
    public Shop createShop(@NonNull final CreateShopRequest createShopRequest) {
        return shopRepository.createShop(Shop.of(createShopRequest));
    }
}
