package com.example.workshift

import com.example.workshift.business.shift.CreateShiftRequest
import com.example.workshift.business.shift.Shift
import com.example.workshift.business.shop.CreateShopRequest
import com.example.workshift.business.user.CreateUserRequest

import java.time.Duration
import java.time.Instant

class TestDataProvider {
    public static final Long USER_ID = 1L
    public static final Long SHOP_ID = 1L
    public static final Instant NOW = Instant.now()
    public static final Instant TOMORROW = NOW + Duration.ofDays(1L)

    public static final String USER_FIRST_NAME = "first_name"
    public static final String USER_LAST_NAME = "last_name"
    public static final String USER_ADDRESS = "user_address"
    public static final String USER_PHONE_NUMBER = "88888888"
    public static final String USER_EMAIL = "user@gmail.com"

    public static final String SHOP1_NAME = "shop1_name"
    public static final String CVR1 = "cvr1"
    public static final String SHOP1_ADDRESS = "shop1_address"
    public static final String SHOP1_PHONE_NUMBER = "11111111"
    public static final String SHOP1_EMAIL = "shop1@gmail.com"

    public static final String SHOP2_NAME = "shop2_name"
    public static final String CVR2 = "cvr2"
    public static final String SHOP2_ADDRESS = "shop2_address"
    public static final String SHOP2_PHONE_NUMBER = "22222222"
    public static final String SHOP2_EMAIL = "shop2@gmail.com"

    static CreateUserRequest createUserRequest() {
        new CreateUserRequest(USER_FIRST_NAME, USER_LAST_NAME, USER_ADDRESS, USER_PHONE_NUMBER, USER_EMAIL)
    }

    static CreateShopRequest createShopRequest1() {
        new CreateShopRequest(SHOP1_NAME, CVR1, SHOP1_ADDRESS, SHOP1_PHONE_NUMBER, SHOP1_EMAIL)
    }

    static CreateShopRequest createShopRequest2() {
        new CreateShopRequest(SHOP2_NAME, CVR2, SHOP2_ADDRESS, SHOP2_PHONE_NUMBER, SHOP2_EMAIL)
    }

    static CreateShiftRequest createShiftRequest(Instant activeFrom, Instant activeTo) {
        new CreateShiftRequest(activeFrom, activeTo, USER_ID, SHOP_ID)
    }

    static Shift createNewShift() {
        new Shift(null, NOW, NOW + Duration.ofHours(2), USER_ID, SHOP_ID)
    }

}
