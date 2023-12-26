package com.example.workshift.it

import com.example.workshift.business.exception.ActiveShiftException
import com.example.workshift.business.shift.CreateShiftRequest
import com.example.workshift.business.shift.Shift
import com.example.workshift.business.shop.Shop
import com.example.workshift.business.user.User

import java.time.Duration

import static com.example.workshift.TestDataProvider.TOMORROW
import static com.example.workshift.TestDataProvider.createShopRequest1
import static com.example.workshift.TestDataProvider.createShopRequest2
import static com.example.workshift.TestDataProvider.createUserRequest

class ShiftCreationSameTimeValidationSpec extends BaseIntegrationSpec {

    def "verify creation of a shift is not allowed when user is working at the same time"() {
        given:
            User createdUser = userService.createUser(createUserRequest())
            Shop createdShop1 = shopService.createShop(createShopRequest1())
            Shop createdShop2 = shopService.createShop(createShopRequest2())
            shiftService.createShift(new CreateShiftRequest(TOMORROW, TOMORROW + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
        when:
            shiftService.createShift(new CreateShiftRequest(activeFrom, activeTo, createdUser.id(), createdShop2.id()))
        then:
            thrown(ActiveShiftException)
        where:
            activeFrom                     | activeTo
            TOMORROW - Duration.ofHours(1) | TOMORROW
            TOMORROW - Duration.ofHours(1) | TOMORROW + Duration.ofHours(1)
            TOMORROW                       | TOMORROW + Duration.ofHours(1)
            TOMORROW                       | TOMORROW + Duration.ofHours(2)
            TOMORROW + Duration.ofHours(1) | TOMORROW + Duration.ofHours(2)
            TOMORROW + Duration.ofHours(1) | TOMORROW + Duration.ofHours(3)
            TOMORROW + Duration.ofHours(2) | TOMORROW + Duration.ofHours(3)
    }

    def "verify creation of a shift when user is not working at the same time"() {
        given:
            User createdUser = userService.createUser(createUserRequest())
            Shop createdShop1 = shopService.createShop(createShopRequest1())
            Shop createdShop2 = shopService.createShop(createShopRequest2())
            Shift existingShift = shiftService.createShift(new CreateShiftRequest(TOMORROW, TOMORROW + Duration.ofHours(2), createdUser.id(), createdShop1.id()))
        when:
            Shift newSavedShift = shiftService.createShift(new CreateShiftRequest(activeFrom, activeTo, createdUser.id(), createdShop2.id()))
        then:
            noExceptionThrown()
            newSavedShift.id() != null
            newSavedShift.id() != existingShift.id()
        where:
            activeFrom                                            | activeTo
            TOMORROW - Duration.ofHours(1)                        | TOMORROW - Duration.ofMillis(1)
            TOMORROW + Duration.ofHours(2) + Duration.ofMillis(1) | TOMORROW + Duration.ofHours(3)
    }
}
