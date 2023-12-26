package com.example.workshift.it

import com.example.workshift.rest.shop.CreateShopRequestDto
import org.springframework.http.MediaType

import static com.example.workshift.TestDataProvider.CVR1
import static com.example.workshift.TestDataProvider.SHOP1_ADDRESS
import static com.example.workshift.TestDataProvider.SHOP1_EMAIL
import static com.example.workshift.TestDataProvider.SHOP1_NAME
import static com.example.workshift.TestDataProvider.SHOP1_PHONE_NUMBER
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class ShopApiSpec extends BaseApiSpec {

    private static final String SHOP_API_PATH = "/api/v1/shops"

    def "verify shop creation"() {
        given:
            CreateShopRequestDto createShopRequestDto = new CreateShopRequestDto(SHOP1_NAME, CVR1, SHOP1_ADDRESS, SHOP1_PHONE_NUMBER, SHOP1_EMAIL)
        when:
            def result = mockMvc.perform(post(SHOP_API_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createShopRequestDto))).andReturn()
        then:
            result.getResponse().getStatus() == 200
            result.getResponse().getContentAsString() != null
    }
}
