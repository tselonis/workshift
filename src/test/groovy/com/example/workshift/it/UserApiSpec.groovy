package com.example.workshift.it

import com.example.workshift.rest.user.CreateUserRequestDto
import org.springframework.http.MediaType

import static com.example.workshift.TestDataProvider.USER_ADDRESS
import static com.example.workshift.TestDataProvider.USER_EMAIL
import static com.example.workshift.TestDataProvider.USER_FIRST_NAME
import static com.example.workshift.TestDataProvider.USER_LAST_NAME
import static com.example.workshift.TestDataProvider.USER_PHONE_NUMBER
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class UserApiSpec extends BaseApiSpec {

    private static final String USER_API_PATH = "/api/v1/users"

    def "verify user creation"() {
        given:
            CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto(USER_FIRST_NAME, USER_LAST_NAME, USER_ADDRESS, USER_PHONE_NUMBER, USER_EMAIL)
        when:
            def result = mockMvc.perform(post(USER_API_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createUserRequestDto))).andReturn()
        then:
            result.getResponse().getStatus() == 200
            result.getResponse().getContentAsString() != null
    }
}
