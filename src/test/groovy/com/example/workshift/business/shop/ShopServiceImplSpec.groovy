package com.example.workshift.business.shop

import spock.lang.Specification

import static com.example.workshift.TestDataProvider.createShopRequest1

class ShopServiceImplSpec extends Specification {

    ShopRepository shopRepository
    ShopService shopService

    def setup() {
        shopRepository = Mock()
        shopService = new ShopServiceImpl(shopRepository)
    }

    def "verify create shop null guards"() {
        when:
            shopService.createShop(null)
        then:
            thrown(NullPointerException)
    }

    def "verify create shop"() {
        when:
            shopService.createShop(createShopRequest1())
        then:
            1 * shopRepository.createShop(_ as Shop)
    }
}
