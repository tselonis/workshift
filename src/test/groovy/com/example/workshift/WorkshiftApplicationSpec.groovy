package com.example.workshift

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class WorkshiftApplicationSpec extends Specification {

    def "contextLoads"() {
        expect:
            true
    }
}
