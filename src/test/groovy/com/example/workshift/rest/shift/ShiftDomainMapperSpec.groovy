package com.example.workshift.rest.shift

import com.example.workshift.business.shift.CreateShiftRequest
import spock.lang.Shared
import spock.lang.Specification

import java.time.Duration

import static com.example.workshift.TestDataProvider.SHOP_ID
import static com.example.workshift.TestDataProvider.TOMORROW
import static com.example.workshift.TestDataProvider.USER_ID

class ShiftDomainMapperSpec extends Specification {

    @Shared
    ShiftDomainMapper shiftDomainMapper = new ShiftDomainMapper()

    def "verify create shift request mapping null guards"() {
        when:
            shiftDomainMapper.mapToCreateShiftRequest(null)
        then:
            thrown(NullPointerException)
    }

    def "verify create shift request mapping"() {
        given:
            CreateShiftRequestDto createShiftRequestDto = new CreateShiftRequestDto(TOMORROW, TOMORROW + Duration.ofHours(2), USER_ID, SHOP_ID)
        when:
            CreateShiftRequest createShiftRequest = shiftDomainMapper.mapToCreateShiftRequest(createShiftRequestDto)
        then:
            verifyAll(createShiftRequest) {
                activeFrom() == createShiftRequestDto.activeFrom()
                activeTo() == createShiftRequestDto.activeTo()
                userId() == createShiftRequestDto.userId()
                shopId() == createShiftRequestDto.shopId()
            }
    }
}
