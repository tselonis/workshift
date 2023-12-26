package com.example.workshift.persistence.shop;

import com.example.workshift.business.shop.Shop;
import com.example.workshift.business.shop.ShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
class H2ShopRepository implements ShopRepository {

    private ShopSpringDataRepository shopSpringDataRepository;
    private ShopDbMapper shopDbMapper;

    @Override
    public Shop createShop(final Shop shop) {
        final ShopEntity shopEntity = shopDbMapper.mapToShopEntityForCreate(shop);
        final ShopEntity savedShop = shopSpringDataRepository.save(shopEntity);

        return shopDbMapper.mapToShop(savedShop);
    }
}
