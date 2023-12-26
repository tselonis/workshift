package com.example.workshift.persistence.shift

import com.example.workshift.business.shift.Shift
import com.example.workshift.persistence.shop.ShopEntity
import com.example.workshift.persistence.user.UserEntity
import spock.lang.Shared
import spock.lang.Specification

import java.time.Duration

import static com.example.workshift.TestDataProvider.CVR1
import static com.example.workshift.TestDataProvider.SHOP1_ADDRESS
import static com.example.workshift.TestDataProvider.SHOP1_EMAIL
import static com.example.workshift.TestDataProvider.SHOP1_NAME
import static com.example.workshift.TestDataProvider.SHOP1_PHONE_NUMBER
import static com.example.workshift.TestDataProvider.SHOP_ID
import static com.example.workshift.TestDataProvider.TOMORROW
import static com.example.workshift.TestDataProvider.USER_ADDRESS
import static com.example.workshift.TestDataProvider.USER_EMAIL
import static com.example.workshift.TestDataProvider.USER_FIRST_NAME
import static com.example.workshift.TestDataProvider.USER_ID
import static com.example.workshift.TestDataProvider.USER_LAST_NAME
import static com.example.workshift.TestDataProvider.USER_PHONE_NUMBER

class ShiftDbMapperSpec extends Specification {

    @Shared
    ShiftDbMapper shiftDbMapper = new ShiftDbMapper()

    def "verify shift entity for create mapping"() {
        given:
            Shift newShift = new Shift(null, TOMORROW, TOMORROW + Duration.ofHours(2), USER_ID, SHOP_ID)
            UserEntity userEntity = getSavedUserEntity()
            ShopEntity shopEntity = getSavedShopEntity()
        when:
            ShiftEntity shiftEntity = shiftDbMapper.mapToShiftEntityForCreate(newShift, userEntity, shopEntity)
        then:
            verifyAll(shiftEntity) {
                getId() == null
                getVersion() == null
                getActiveFrom() == newShift.activeFrom()
                getActiveTo() == newShift.activeTo()
                getUser() == userEntity
                getShop() == shopEntity
            }
    }

    def "verify entity to shift mapping"() {
        given:
            Shift newShift = new Shift(null, TOMORROW, TOMORROW + Duration.ofHours(2), USER_ID, SHOP_ID)
            UserEntity userEntity = getSavedUserEntity()
            ShopEntity shopEntity = getSavedShopEntity()
            ShiftEntity shiftEntity = getSavedShiftEntity(newShift, userEntity, shopEntity)
        when:
            Shift savedShift = shiftDbMapper.mapToShift(shiftEntity)
        then:
            verifyAll(savedShift) {
                id() == shiftEntity.getId()
                activeFrom() == shiftEntity.getActiveFrom()
                activeTo() == shiftEntity.getActiveTo()
                userId() == shiftEntity.getUser().getId()
                shopId() == shiftEntity.getShop().getId()
            }
    }

    private ShopEntity getSavedShopEntity() {
        ShopEntity shopEntity = new ShopEntity()
        shopEntity.setId(1L)
        shopEntity.setVersion(1L)
        shopEntity.setName(SHOP1_NAME)
        shopEntity.setCvr(CVR1)
        shopEntity.setAddress(SHOP1_ADDRESS)
        shopEntity.setPhoneNumber(SHOP1_PHONE_NUMBER)
        shopEntity.setEmail(SHOP1_EMAIL)

        return shopEntity
    }

    private UserEntity getSavedUserEntity() {
        UserEntity userEntity = new UserEntity()
        userEntity.setId(1L)
        userEntity.setVersion(1L)
        userEntity.setFirstName(USER_FIRST_NAME)
        userEntity.setLastName(USER_LAST_NAME)
        userEntity.setAddress(USER_ADDRESS)
        userEntity.setPhoneNumber(USER_PHONE_NUMBER)
        userEntity.setEmail(USER_EMAIL)

        return userEntity
    }

    private ShiftEntity getSavedShiftEntity(Shift shift, UserEntity userEntity, ShopEntity shopEntity) {
        ShiftEntity shiftEntity = new ShiftEntity()
        shiftEntity.setId(1L)
        shiftEntity.setVersion(1L)
        shiftEntity.setActiveFrom(shift.activeFrom())
        shiftEntity.setActiveTo(shift.activeTo())
        shiftEntity.setUser(userEntity)
        shiftEntity.setShop(shopEntity)

        return shiftEntity
    }
}
