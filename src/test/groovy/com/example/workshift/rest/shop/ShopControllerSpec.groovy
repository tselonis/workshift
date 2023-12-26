package com.example.workshift.rest.shop

import com.example.workshift.business.shop.CreateShopRequest
import com.example.workshift.business.shop.Shop
import com.example.workshift.business.shop.ShopService
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static com.example.workshift.TestDataProvider.CVR1
import static com.example.workshift.TestDataProvider.SHOP1_ADDRESS
import static com.example.workshift.TestDataProvider.SHOP1_EMAIL
import static com.example.workshift.TestDataProvider.SHOP1_NAME
import static com.example.workshift.TestDataProvider.SHOP1_PHONE_NUMBER

class ShopControllerSpec extends Specification {

    ShopDomainMapper shopDomainMapper
    ShopService shopService
    ShopController shopController

    def setup() {
        shopDomainMapper = Mock()
        shopService = Mock()
        shopController = new ShopController(shopDomainMapper, shopService)
    }

    def "verify create shop"() {
        given:
            CreateShopRequestDto createShopRequestDto = new CreateShopRequestDto(SHOP1_NAME, CVR1, SHOP1_ADDRESS, SHOP1_PHONE_NUMBER, SHOP1_EMAIL)
            CreateShopRequest createShopRequest = new CreateShopRequest(SHOP1_NAME, CVR1, SHOP1_ADDRESS, SHOP1_PHONE_NUMBER, SHOP1_EMAIL)
            Shop createdShop = new Shop(1L, SHOP1_NAME, CVR1, SHOP1_ADDRESS, SHOP1_PHONE_NUMBER, SHOP1_EMAIL)
        when:
            ResponseEntity<Long> responseEntity = shopController.createShop(createShopRequestDto)
        then:
            1 * shopDomainMapper.mapToCreateShopRequest(createShopRequestDto) >> createShopRequest
            1 * shopService.createShop(createShopRequest) >> createdShop
            responseEntity.getBody() == createdShop.id()
    }
}
