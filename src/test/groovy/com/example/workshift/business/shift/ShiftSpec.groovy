package com.example.workshift.business.shift

import com.example.workshift.business.exception.InvalidShiftDurationException
import com.example.workshift.business.exception.ShiftDurationMoreThanEightHoursException
import spock.lang.Specification

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

import static com.example.workshift.TestDataProvider.NOW
import static com.example.workshift.TestDataProvider.SHOP_ID
import static com.example.workshift.TestDataProvider.USER_ID
import static com.example.workshift.TestDataProvider.createNewShift

class ShiftSpec extends Specification {

    def "verify constructor null guards"() {
        when:
            new Shift(null, activeFrom, activeTo, userId, shopId)
        then:
            thrown(NullPointerException)
        where:
            activeFrom  | activeTo    | userId  | shopId
            null        | Instant.MAX | USER_ID | SHOP_ID
            Instant.MIN | null        | USER_ID | SHOP_ID
            Instant.MIN | Instant.MAX | null    | SHOP_ID
            Instant.MIN | Instant.MAX | USER_ID | null
    }

    def "verify duration validation"() {
        when:
            new Shift(null, activeFrom, activeTo, USER_ID, SHOP_ID)
        then:
            thrown(InvalidShiftDurationException)
        where:
            activeFrom | activeTo
            NOW        | NOW
            NOW        | NOW.minusMillis(1L)
    }

    def "verify duration max eight hours validation"() {
        when:
            new Shift(null, activeFrom, activeTo, USER_ID, SHOP_ID)
        then:
            thrown(ShiftDurationMoreThanEightHoursException)
        where:
            activeFrom | activeTo
            NOW        | NOW + Duration.ofHours(8) + Duration.ofMillis(1L)
    }

    def "verify static factory method from create request null guards"() {
        when:
            Shift.of(null)
        then:
            thrown(NullPointerException)
    }

    def "verify static factory method from create request"() {
        given:
            CreateShiftRequest createShiftRequest = new CreateShiftRequest(NOW, NOW + Duration.ofHours(8), USER_ID, SHOP_ID)
        when:
            Shift shiftInstance = Shift.of(createShiftRequest)
        then:
            verifyAll(shiftInstance) {
                id() == null
                activeFrom() == createShiftRequest.activeFrom()
                activeTo() == createShiftRequest.activeTo()
                userId() == createShiftRequest.userId()
                shopId() == createShiftRequest.shopId()
            }
    }

    def "verify adjust active from"() {
        given:
            Shift shift = createNewShift()
        when:
            Shift adjustedShift = shift.adjustActiveFrom(NOW + Duration.ofHours(1))
        then:
            adjustedShift.id() == shift.id()
            adjustedShift.activeFrom() == NOW + Duration.ofHours(1)
            adjustedShift.activeTo() == shift.activeTo()
            adjustedShift.userId() == shift.userId()
            adjustedShift.shopId() == adjustedShift.shopId()
    }

    def "verify adjust active to"() {
        given:
            Shift shift = createNewShift()
        when:
            Shift adjustedShift = shift.adjustActiveTo(NOW + Duration.ofHours(1))
        then:
            adjustedShift.id() == shift.id()
            adjustedShift.activeFrom() == shift.activeFrom()
            adjustedShift.activeTo() == NOW + Duration.ofHours(1)
            adjustedShift.userId() == shift.userId()
            adjustedShift.shopId() == adjustedShift.shopId()
    }

    def "verify enclose timestamp"() {
        when:
            boolean response = createNewShift().encloseTimestamp(timestamp)
        then:
            response == expectedResponse
        where:
            timestamp                 || expectedResponse
            NOW                       || false
            NOW + Duration.ofHours(2) || false
            NOW + Duration.ofHours(1) || true
    }

    def "verify calculation of duration in milliseconds"() {
        expect:
            createNewShift().calculateDurationInMs() == Duration.ofHours(2).toMillis()
    }

    def "verify start of 5 day window"() {
        when:
            def result = new Shift(1L, activeFrom, activeTo, USER_ID, SHOP_ID).getStartOf5DayWindow()
        then:
            result == expectedResult
        where:
            activeFrom                            | activeTo                              || expectedResult
            Instant.parse("2023-12-24T01:00:00Z") | Instant.parse("2023-12-24T05:00:00Z") || Instant.parse("2023-12-19T00:00:00Z")
            Instant.parse("2023-12-24T23:00:00Z") | Instant.parse("2023-12-25T01:00:00Z") || Instant.parse("2023-12-20T00:00:00Z")
    }

    def "verify end of 5 day window"() {
        when:
            def result = new Shift(1L, activeFrom, activeTo, USER_ID, SHOP_ID).getEndOf5DayWindow()
        then:
            result.truncatedTo(ChronoUnit.SECONDS) == expectedResult
        where:
            activeFrom                            | activeTo                              || expectedResult
            Instant.parse("2023-12-24T01:00:00Z") | Instant.parse("2023-12-24T05:00:00Z") || Instant.parse("2023-12-29T23:59:59Z")
            Instant.parse("2023-12-24T23:00:00Z") | Instant.parse("2023-12-25T01:00:00Z") || Instant.parse("2023-12-29T23:59:59Z")
    }

    def "verify start and end of 5 day window"() {
        when:
            def start = new Shift(1L, activeFrom, activeTo, USER_ID, SHOP_ID).getStartOf5DayWindow()
            def end = new Shift(1L, activeFrom, activeTo, USER_ID, SHOP_ID).getEndOf5DayWindow()
        then:
            start.truncatedTo(ChronoUnit.SECONDS) == expectedStart
            end.truncatedTo(ChronoUnit.SECONDS) == expectedEnd
        where:
            activeFrom                            | activeTo                              | expectedStart                         | expectedEnd
            Instant.parse("2023-12-24T01:00:00Z") | Instant.parse("2023-12-24T05:00:00Z") | Instant.parse("2023-12-19T00:00:00Z") | Instant.parse("2023-12-29T23:59:59Z")
            Instant.parse("2023-12-24T23:00:00Z") | Instant.parse("2023-12-25T01:00:00Z") | Instant.parse("2023-12-20T00:00:00Z") | Instant.parse("2023-12-29T23:59:59Z")
    }

    def "verify distinct days"() {
        given:
            Shift shift = new Shift(null, activeFrom, activeTo, USER_ID, SHOP_ID)
        when:
            List<LocalDate> dates = shift.getDistinctDaysOfDuration()
        then:
            dates == expectedDates
        where:
            activeFrom                            | activeTo                              || expectedDates
            Instant.parse("2023-12-24T01:00:00Z") | Instant.parse("2023-12-24T05:00:00Z") || [LocalDate.parse("2023-12-24")]
            Instant.parse("2023-12-24T23:00:00Z") | Instant.parse("2023-12-25T01:00:00Z") || [LocalDate.parse("2023-12-24"), LocalDate.parse("2023-12-25")]
    }
}
