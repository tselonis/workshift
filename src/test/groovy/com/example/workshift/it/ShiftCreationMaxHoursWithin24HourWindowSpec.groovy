package com.example.workshift.it


import com.example.workshift.business.exception.MaxHoursInTheSameShopWithin24HourWindowExceededException
import com.example.workshift.business.shift.CreateShiftRequest
import com.example.workshift.business.shift.Shift
import com.example.workshift.business.shop.Shop
import com.example.workshift.business.user.User

import java.time.Duration
import java.time.Instant

import static com.example.workshift.TestDataProvider.TOMORROW
import static com.example.workshift.TestDataProvider.createShopRequest1
import static com.example.workshift.TestDataProvider.createUserRequest

class ShiftCreationMaxHoursWithin24HourWindowSpec extends BaseIntegrationSpec {
    def "verify creation of a shift is not allowed when user is working in the same shop more than 8 hours within 24hour window "() {
        given:
            User createdUser = userService.createUser(createUserRequest())
            Shop createdShop1 = shopService.createShop(createShopRequest1())
            Instant beginningOf24HWindow = TOMORROW
            Instant endingOf24HWindow = TOMORROW + Duration.ofHours(24)
            Shift shiftOf1Hour = shiftService.createShift(new CreateShiftRequest(beginningOf24HWindow, beginningOf24HWindow + Duration.ofHours(1), createdUser.id(), createdShop1.id()))
            Shift shiftOf5Hours = shiftService.createShift(new CreateShiftRequest(endingOf24HWindow - Duration.ofHours(4), endingOf24HWindow + Duration.ofHours(1), createdUser.id(), createdShop1.id()))
        when:
            Shift shiftOf4Hours = shiftService.createShift(new CreateShiftRequest(beginningOf24HWindow + Duration.ofHours(10), beginningOf24HWindow + Duration.ofHours(14), createdUser.id(), createdShop1.id()))
        then:
            shiftOf4Hours == null
            thrown(MaxHoursInTheSameShopWithin24HourWindowExceededException)
        when:
            Shift shiftOf3Hours = shiftService.createShift(new CreateShiftRequest(beginningOf24HWindow + Duration.ofHours(10), beginningOf24HWindow + Duration.ofHours(13), createdUser.id(), createdShop1.id()))
        then:
            shiftOf3Hours
            noExceptionThrown()
    }
}
