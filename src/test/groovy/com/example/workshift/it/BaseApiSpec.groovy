package com.example.workshift.it

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc

@AutoConfigureMockMvc
class BaseApiSpec extends BaseIntegrationSpec {

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    MockMvc mockMvc
}
