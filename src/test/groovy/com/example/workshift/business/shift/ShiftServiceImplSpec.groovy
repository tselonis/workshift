package com.example.workshift.business.shift

import com.example.workshift.business.exception.ActiveShiftException
import com.example.workshift.business.exception.MaxHoursInTheSameShopWithin24HourWindowExceededException
import com.example.workshift.business.exception.MoreThanFiveDaysInARowInTheSameShopException
import com.example.workshift.business.exception.ShiftCreationInThePastException
import spock.lang.Specification

import java.time.Duration
import java.time.Instant

import static com.example.workshift.TestDataProvider.NOW
import static com.example.workshift.TestDataProvider.SHOP_ID
import static com.example.workshift.TestDataProvider.TOMORROW
import static com.example.workshift.TestDataProvider.USER_ID
import static com.example.workshift.TestDataProvider.createShiftRequest

class ShiftServiceImplSpec extends Specification {

    ShiftRepository shiftRepository
    ShiftServiceImpl shiftService

    def setup() {
        shiftRepository = Mock()
        shiftService = new ShiftServiceImpl(shiftRepository)
    }

    def "verify create shift null guards"() {
        when:
            shiftService.createShift(null)
        then:
            thrown(NullPointerException)
    }

    def "verify create shift should not have duration in the past"() {
        when:
            shiftService.createShift(createShiftRequest(activeFrom, activeFrom + Duration.ofHours(2)))
        then:
            thrown(ShiftCreationInThePastException)
        where:
            activeFrom << [NOW.minusMillis(10), NOW]
    }

    def "verify exception thrown if user is working at the same time"() {
        given:
            Instant activeFrom = TOMORROW
            Instant activeTo = TOMORROW + Duration.ofHours(2)
            CreateShiftRequest createShiftRequest = createShiftRequest(activeFrom, activeTo)
            Shift existingShift = new Shift(1L, activeFrom, activeTo, USER_ID, SHOP_ID)
        when:
            shiftService.createShift(createShiftRequest)
        then:
            1 * shiftRepository.findShiftByUserIdAndOverlappingDuration(USER_ID, activeFrom, activeTo) >> [existingShift]
            thrown(ActiveShiftException)
    }

    def "verify exception thrown if user is working in the same shop for more than 8 hours"() {
        given:
            Shift existingShift = new Shift(1L, TOMORROW, TOMORROW + Duration.ofHours(8), USER_ID, SHOP_ID)
        when:
            shiftService.createShift(createShiftRequest(TOMORROW + Duration.ofHours(10), TOMORROW + Duration.ofHours(12)))
        then:
            1 * shiftRepository.findShiftByUserIdAndOverlappingDuration(USER_ID, _ as Instant, _ as Instant) >> []
        then:
            1 * shiftRepository.findShiftByUserIdAndShopIdWithin(USER_ID, SHOP_ID, _ as Instant, _ as Instant) >> [existingShift]
            thrown(MaxHoursInTheSameShopWithin24HourWindowExceededException)
    }

    def "verify exception thrown if user is working in the same shop for more than 5 days in a row"() {
        given:
            Shift existingShift1 = new Shift(1L, TOMORROW, TOMORROW + Duration.ofHours(2), USER_ID, SHOP_ID)
            Shift existingShift2 = new Shift(1L, TOMORROW + Duration.ofDays(1), TOMORROW + Duration.ofDays(1) + Duration.ofHours(2), USER_ID, SHOP_ID)
            Shift existingShift3 = new Shift(1L, TOMORROW + Duration.ofDays(2), TOMORROW + Duration.ofDays(2) + Duration.ofHours(2), USER_ID, SHOP_ID)
            Shift existingShift4 = new Shift(1L, TOMORROW + Duration.ofDays(3), TOMORROW + Duration.ofDays(3) + Duration.ofHours(2), USER_ID, SHOP_ID)
            Shift existingShift5 = new Shift(1L, TOMORROW + Duration.ofDays(4), TOMORROW + Duration.ofDays(4) + Duration.ofHours(2), USER_ID, SHOP_ID)
            Instant activeFromForShift6 = TOMORROW + Duration.ofDays(5)
        when:
            shiftService.createShift(createShiftRequest(activeFromForShift6, activeFromForShift6 + Duration.ofHours(2)))
        then:
            1 * shiftRepository.findShiftByUserIdAndOverlappingDuration(USER_ID, _ as Instant, _ as Instant) >> []
        then:
            1 * shiftRepository.findShiftByUserIdAndShopIdWithin(USER_ID, SHOP_ID, _ as Instant, _ as Instant) >> []
        then:
            1 * shiftRepository.findShiftByUserIdAndShopIdWithin(USER_ID, SHOP_ID, _ as Instant, _ as Instant) >> [existingShift1, existingShift2, existingShift3, existingShift4, existingShift5]
            thrown(MoreThanFiveDaysInARowInTheSameShopException)
    }
}
