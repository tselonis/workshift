package com.example.workshift.persistence.shop

import com.example.workshift.business.shop.Shop
import spock.lang.Shared
import spock.lang.Specification

import static com.example.workshift.TestDataProvider.CVR1
import static com.example.workshift.TestDataProvider.SHOP1_ADDRESS
import static com.example.workshift.TestDataProvider.SHOP1_EMAIL
import static com.example.workshift.TestDataProvider.SHOP1_NAME
import static com.example.workshift.TestDataProvider.SHOP1_PHONE_NUMBER
import static com.example.workshift.TestDataProvider.createShopRequest1

class ShopDbMapperSpec extends Specification {

    @Shared
    ShopDbMapper shopDbMapper = new ShopDbMapper()

    def "verify domain to shop entity mapping for create"() {
        given:
            Shop shop = Shop.of(createShopRequest1())
        when:
            ShopEntity shopEntity = shopDbMapper.mapToShopEntityForCreate(shop)
        then:
            shopEntity.getId() == null
            shopEntity.getVersion() == null
            shopEntity.getName() == shop.name()
            shopEntity.getCvr() == shop.cvr()
            shopEntity.getAddress() == shop.address()
            shopEntity.getPhoneNumber() == shop.phoneNumber()
            shopEntity.getEmail() == shop.email()
    }

    def "verify shop entity to domain mapping"() {
        given:
            ShopEntity shopEntity = new ShopEntity()
            shopEntity.setId(1L)
            shopEntity.setVersion(1L)
            shopEntity.setName(SHOP1_NAME)
            shopEntity.setCvr(CVR1)
            shopEntity.setAddress(SHOP1_ADDRESS)
            shopEntity.setPhoneNumber(SHOP1_PHONE_NUMBER)
            shopEntity.setEmail(SHOP1_EMAIL)
        when:
            Shop shop = shopDbMapper.mapToShop(shopEntity)
        then:
            shop.id() == shopEntity.getId()
            shop.name() == shopEntity.getName()
            shop.cvr() == shopEntity.getCvr()
            shop.address() == shopEntity.getAddress()
            shop.phoneNumber() == shopEntity.getPhoneNumber()
            shop.email() == shopEntity.getEmail()
    }
}
