package com.example.workshift.it

import com.example.workshift.business.exception.MoreThanFiveDaysInARowInTheSameShopException
import com.example.workshift.business.shift.CreateShiftRequest
import com.example.workshift.business.shift.Shift
import com.example.workshift.business.shop.Shop
import com.example.workshift.business.user.User

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

import static com.example.workshift.TestDataProvider.TOMORROW
import static com.example.workshift.TestDataProvider.createShopRequest1
import static com.example.workshift.TestDataProvider.createUserRequest

class ShiftCreationMoreThan5DaysInARowSpec extends BaseIntegrationSpec {
    private static final String UTC = "UTC"

    def "verify creation of a shift is not allowed when user is working more than 5 consecutive days in the same shop"() {
        given:
            User createdUser = userService.createUser(createUserRequest())
            Shop createdShop1 = shopService.createShop(createShopRequest1())
            Shift existingShiftDay1 = shiftService.createShift(new CreateShiftRequest(TOMORROW + Duration.ofDays(1), TOMORROW + Duration.ofDays(1) + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
            Shift existingShiftDay2 = shiftService.createShift(new CreateShiftRequest(TOMORROW + Duration.ofDays(2), TOMORROW + Duration.ofDays(2) + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
            Shift existingShiftDay3 = shiftService.createShift(new CreateShiftRequest(TOMORROW + Duration.ofDays(3), TOMORROW + Duration.ofDays(3) + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
            Shift existingShiftDay4 = shiftService.createShift(new CreateShiftRequest(TOMORROW + Duration.ofDays(4), TOMORROW + Duration.ofDays(4) + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
            Shift existingShiftDay5 = shiftService.createShift(new CreateShiftRequest(TOMORROW + Duration.ofDays(5), TOMORROW + Duration.ofDays(5) + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
            Shift existingShiftDay8 = shiftService.createShift(new CreateShiftRequest(TOMORROW + Duration.ofDays(8), TOMORROW + Duration.ofDays(8) + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
            Shift existingShiftDay9 = shiftService.createShift(new CreateShiftRequest(TOMORROW + Duration.ofDays(9), TOMORROW + Duration.ofDays(9) + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
        when:
            Shift existingShiftDay6 = shiftService.createShift(new CreateShiftRequest(TOMORROW + Duration.ofDays(6), TOMORROW + Duration.ofDays(6) + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
        then:
            thrown(MoreThanFiveDaysInARowInTheSameShopException)
        when:
            Shift existingShiftDay6To7 = shiftService.createShift(new CreateShiftRequest(getEndOfDay(TOMORROW + Duration.ofDays(6)), getEndOfDay(TOMORROW + Duration.ofDays(6)) + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
        then:
            thrown(MoreThanFiveDaysInARowInTheSameShopException)
        when:
            Shift existingShiftDay7 = shiftService.createShift(new CreateShiftRequest(TOMORROW + Duration.ofDays(7), TOMORROW + Duration.ofDays(7) + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
        then:
            noExceptionThrown()
            existingShiftDay7.id() != null
    }

    private Instant getEndOfDay(Instant timestamp) {
        LocalDate.ofInstant(timestamp, ZoneId.of(UTC)).atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC)
    }
}
