package com.example.workshift.rest.shop

import com.example.workshift.business.shop.CreateShopRequest
import spock.lang.Shared
import spock.lang.Specification

import static com.example.workshift.TestDataProvider.CVR1
import static com.example.workshift.TestDataProvider.SHOP1_ADDRESS
import static com.example.workshift.TestDataProvider.SHOP1_EMAIL
import static com.example.workshift.TestDataProvider.SHOP1_NAME
import static com.example.workshift.TestDataProvider.SHOP1_PHONE_NUMBER

class ShopDomainMapperSpec extends Specification {

    @Shared
    ShopDomainMapper shopDomainMapper = new ShopDomainMapper()

    def "verify create shop request mapping null guards"() {
        when:
            shopDomainMapper.mapToCreateShopRequest(null)
        then:
            thrown(NullPointerException)
    }

    def "verify create shop request mapping"() {
        given:
            CreateShopRequestDto createShopRequestDto = new CreateShopRequestDto(SHOP1_NAME, CVR1, SHOP1_ADDRESS, SHOP1_PHONE_NUMBER, SHOP1_EMAIL)
        when:
            CreateShopRequest createShopRequest = shopDomainMapper.mapToCreateShopRequest(createShopRequestDto)
        then:
            verifyAll(createShopRequest) {
                name() == createShopRequestDto.name()
                cvr() == createShopRequestDto.cvr()
                address() == createShopRequestDto.address()
                phoneNumber() == createShopRequestDto.phoneNumber()
                email() == createShopRequestDto.email()
            }
    }
}
