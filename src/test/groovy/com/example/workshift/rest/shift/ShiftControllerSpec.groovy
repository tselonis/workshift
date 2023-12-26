package com.example.workshift.rest.shift

import com.example.workshift.business.shift.CreateShiftRequest
import com.example.workshift.business.shift.Shift
import com.example.workshift.business.shift.ShiftService
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import java.time.Duration

import static com.example.workshift.TestDataProvider.SHOP_ID
import static com.example.workshift.TestDataProvider.TOMORROW
import static com.example.workshift.TestDataProvider.USER_ID

class ShiftControllerSpec extends Specification {

    ShiftDomainMapper shiftDomainMapper
    ShiftService shiftService
    ShiftController shiftController

    def setup() {
        shiftDomainMapper = Mock()
        shiftService = Mock()
        shiftController = new ShiftController(shiftDomainMapper, shiftService)
    }

    def "verify create shift"() {
        given:
            CreateShiftRequestDto createShiftRequestDto = new CreateShiftRequestDto(TOMORROW, TOMORROW + Duration.ofHours(2), USER_ID, SHOP_ID)
            CreateShiftRequest createShiftRequest = new CreateShiftRequest(TOMORROW, TOMORROW + Duration.ofHours(2), USER_ID, SHOP_ID)
            Shift createdShift = new Shift(1L, TOMORROW, TOMORROW + Duration.ofHours(2), USER_ID, SHOP_ID)
        when:
            ResponseEntity<Long> responseEntity = shiftController.createShift(createShiftRequestDto)
        then:
            1 * shiftDomainMapper.mapToCreateShiftRequest(createShiftRequestDto) >> createShiftRequest
            1 * shiftService.createShift(createShiftRequest) >> createdShift
            responseEntity.getBody() == createdShift.id()
    }

}
