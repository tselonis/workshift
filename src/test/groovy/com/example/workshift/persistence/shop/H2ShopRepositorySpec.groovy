package com.example.workshift.persistence.shop

import com.example.workshift.business.shop.Shop
import com.example.workshift.business.shop.ShopRepository
import spock.lang.Specification

import static com.example.workshift.TestDataProvider.CVR1
import static com.example.workshift.TestDataProvider.SHOP1_ADDRESS
import static com.example.workshift.TestDataProvider.SHOP1_EMAIL
import static com.example.workshift.TestDataProvider.SHOP1_NAME
import static com.example.workshift.TestDataProvider.SHOP1_PHONE_NUMBER

class H2ShopRepositorySpec extends Specification {

    ShopSpringDataRepository shopSpringDataRepository
    ShopDbMapper shopDbMapper
    ShopRepository shopRepository

    def setup() {
        shopSpringDataRepository = Mock()
        shopDbMapper = Mock()
        shopRepository = new H2ShopRepository(shopSpringDataRepository, shopDbMapper)
    }

    def "verify create shop"() {
        given:
            Shop shop = new Shop(null, SHOP1_NAME, CVR1, SHOP1_ADDRESS, SHOP1_PHONE_NUMBER, SHOP1_EMAIL)
            ShopEntity shopEntity = new ShopEntity()
            shopEntity.setName(SHOP1_NAME)
            shopEntity.setCvr(CVR1)
            shopEntity.setAddress(SHOP1_ADDRESS)
            shopEntity.setPhoneNumber(SHOP1_PHONE_NUMBER)
            shopEntity.setEmail(SHOP1_EMAIL)
        when:
            shopRepository.createShop(shop)
        then:
            1 * shopDbMapper.mapToShopEntityForCreate(shop) >> shopEntity
            1 * shopSpringDataRepository.save(shopEntity) >> shopEntity
            1 * shopDbMapper.mapToShop(shopEntity)
    }
}
