package com.example.workshift.it

import com.example.workshift.rest.ErrorResponse
import com.example.workshift.rest.shift.CreateShiftRequestDto
import org.springframework.http.MediaType

import java.time.Duration

import static com.example.workshift.TestDataProvider.SHOP_ID
import static com.example.workshift.TestDataProvider.TOMORROW
import static com.example.workshift.TestDataProvider.USER_ID
import static com.example.workshift.business.exception.ErrorType.SERVER_ERROR
import static com.example.workshift.business.exception.ErrorType.SHIFT_DURATION_INVALID
import static com.example.workshift.business.exception.ErrorType.USER_NOT_FOUND
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class GlobalErrorHandlerSpec extends BaseApiSpec {

    private static final String SHIFT_API_PATH = "/api/v1/shifts"

    def "verify business validation exception handling"() {
        given:
            CreateShiftRequestDto createShiftRequestDto = new CreateShiftRequestDto(TOMORROW + Duration.ofHours(2), TOMORROW, USER_ID, SHOP_ID)
        when:
            def shiftResult = mockMvc.perform(post(SHIFT_API_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createShiftRequestDto))).andReturn()
        then:
            shiftResult.getResponse().getStatus() == 400
            objectMapper.readValue(shiftResult.getResponse().getContentAsString(), ErrorResponse.class).errorCode() == SHIFT_DURATION_INVALID.getErrorCode()
    }

    def "verify persistence exception handling"() {
        given:
            CreateShiftRequestDto createShiftRequestDto = new CreateShiftRequestDto(TOMORROW - Duration.ofHours(2), TOMORROW, USER_ID, SHOP_ID)
        when:
            def shiftResult = mockMvc.perform(post(SHIFT_API_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createShiftRequestDto))).andReturn()
        then:
            shiftResult.getResponse().getStatus() == 400
            objectMapper.readValue(shiftResult.getResponse().getContentAsString(), ErrorResponse.class).errorCode() == USER_NOT_FOUND.getErrorCode()
    }

    def "verify generic exception handling"() {
        given:
            CreateShiftRequestDto createShiftRequestDto = new CreateShiftRequestDto(TOMORROW - Duration.ofHours(2), TOMORROW, null, SHOP_ID)
        when:
            def shiftResult = mockMvc.perform(post(SHIFT_API_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createShiftRequestDto))).andReturn()
        then:
            shiftResult.getResponse().getStatus() == 500
            objectMapper.readValue(shiftResult.getResponse().getContentAsString(), ErrorResponse.class).errorCode() == SERVER_ERROR.getErrorCode()
    }
}
