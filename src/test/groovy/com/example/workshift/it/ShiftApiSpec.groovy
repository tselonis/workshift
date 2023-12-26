package com.example.workshift.it

import com.example.workshift.rest.shift.CreateShiftRequestDto
import com.example.workshift.rest.shop.CreateShopRequestDto
import com.example.workshift.rest.user.CreateUserRequestDto
import org.springframework.http.MediaType

import java.time.Duration

import static com.example.workshift.TestDataProvider.CVR1
import static com.example.workshift.TestDataProvider.SHOP1_ADDRESS
import static com.example.workshift.TestDataProvider.SHOP1_EMAIL
import static com.example.workshift.TestDataProvider.SHOP1_NAME
import static com.example.workshift.TestDataProvider.SHOP1_PHONE_NUMBER
import static com.example.workshift.TestDataProvider.TOMORROW
import static com.example.workshift.TestDataProvider.USER_ADDRESS
import static com.example.workshift.TestDataProvider.USER_EMAIL
import static com.example.workshift.TestDataProvider.USER_FIRST_NAME
import static com.example.workshift.TestDataProvider.USER_LAST_NAME
import static com.example.workshift.TestDataProvider.USER_PHONE_NUMBER
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class ShiftApiSpec extends BaseApiSpec {

    private static final String USER_API_PATH = "/api/v1/users"
    private static final String SHOP_API_PATH = "/api/v1/shops"
    private static final String SHIFT_API_PATH = "/api/v1/shifts"

    def "verify shift creation"() {
        given:
            CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto(USER_FIRST_NAME, USER_LAST_NAME, USER_ADDRESS, USER_PHONE_NUMBER, USER_EMAIL)
            CreateShopRequestDto createShopRequestDto = new CreateShopRequestDto(SHOP1_NAME, CVR1, SHOP1_ADDRESS, SHOP1_PHONE_NUMBER, SHOP1_EMAIL)
            def userResult = mockMvc.perform(post(USER_API_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createUserRequestDto))).andReturn()
            def shopResult = mockMvc.perform(post(SHOP_API_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createShopRequestDto))).andReturn()
            CreateShiftRequestDto createShiftRequestDto = new CreateShiftRequestDto(TOMORROW, TOMORROW + Duration.ofHours(2), Long.parseLong(userResult.getResponse().getContentAsString()), Long.parseLong(shopResult.getResponse().getContentAsString()))
        when:
            def shiftResult = mockMvc.perform(post(SHIFT_API_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createShiftRequestDto))).andReturn()
        then:
            shiftResult.getResponse().getStatus() == 200
            shiftResult.getResponse().getContentAsString() != null
    }
}
