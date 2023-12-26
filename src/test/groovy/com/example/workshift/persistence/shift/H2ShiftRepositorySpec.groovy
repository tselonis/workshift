package com.example.workshift.persistence.shift

import com.example.workshift.business.shift.Shift
import com.example.workshift.business.shift.ShiftRepository
import com.example.workshift.persistence.exception.ShopNotFoundException
import com.example.workshift.persistence.exception.UserNotFoundException
import com.example.workshift.persistence.shop.ShopEntity
import com.example.workshift.persistence.shop.ShopSpringDataRepository
import com.example.workshift.persistence.user.UserEntity
import com.example.workshift.persistence.user.UserSpringDataRepository
import spock.lang.Specification

import java.time.Duration
import java.time.Instant

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

class H2ShiftRepositorySpec extends Specification {

    ShiftSpringDataRepository shiftSpringDataRepository
    UserSpringDataRepository userSpringDataRepository
    ShopSpringDataRepository shopSpringDataRepository
    ShiftDbMapper shiftDbMapper
    ShiftRepository shiftRepository

    def setup() {
        shiftSpringDataRepository = Mock()
        userSpringDataRepository = Mock()
        shopSpringDataRepository = Mock()
        shiftDbMapper = Mock()
        shiftRepository = new H2ShiftRepository(shiftSpringDataRepository, userSpringDataRepository, shopSpringDataRepository, shiftDbMapper)
    }

    def "verify create shift throws exception when user not found"() {
        given:
            Shift newShift = new Shift(null, TOMORROW, TOMORROW + Duration.ofHours(2), USER_ID, SHOP_ID)
        when:
            shiftRepository.createShift(newShift)
        then:
            1 * userSpringDataRepository.findById(USER_ID) >> Optional.empty()
            0 * shopSpringDataRepository._
            0 * shiftDbMapper._
            0 * shiftSpringDataRepository._
            thrown(UserNotFoundException)
    }

    def "verify create shift throws exception when shop not found"() {
        given:
            UserEntity userEntity = getSavedUserEntity()
            Shift newShift = new Shift(null, TOMORROW, TOMORROW + Duration.ofHours(2), USER_ID, SHOP_ID)
        when:
            shiftRepository.createShift(newShift)
        then:
            1 * userSpringDataRepository.findById(USER_ID) >> Optional.of(userEntity)
            1 * shopSpringDataRepository.findById(SHOP_ID) >> Optional.empty()
            0 * shiftDbMapper._
            0 * shiftSpringDataRepository._
            thrown(ShopNotFoundException)
    }

    def "verify create shift"() {
        given:
            UserEntity userEntity = getSavedUserEntity()
            ShopEntity shopEntity = getSavedShopEntity()
            Shift newShift = new Shift(null, TOMORROW, TOMORROW + Duration.ofHours(2), USER_ID, SHOP_ID)
            ShiftEntity shiftEntity = getShiftEntityToBeCreated(newShift, userEntity, shopEntity)
        when:
            shiftRepository.createShift(newShift)
        then:
            1 * userSpringDataRepository.findById(USER_ID) >> Optional.of(userEntity)
            1 * shopSpringDataRepository.findById(SHOP_ID) >> Optional.of(shopEntity)
            1 * shiftDbMapper.mapToShiftEntityForCreate(newShift, userEntity, shopEntity) >> shiftEntity
            1 * shiftSpringDataRepository.save(shiftEntity) >> shiftEntity
            1 * shiftDbMapper.mapToShift(shiftEntity)
    }

    def "verify find shift by user id with overlapping duration"() {
        given:
            Instant from = TOMORROW
            Instant to = TOMORROW + Duration.ofHours(2)
            UserEntity userEntity = getSavedUserEntity()
            ShopEntity shopEntity = getSavedShopEntity()
            Shift newShift = new Shift(null, from, to, USER_ID, SHOP_ID)
            ShiftEntity shiftEntity = getShiftEntityToBeCreated(newShift, userEntity, shopEntity)
        when:
            shiftRepository.findShiftByUserIdAndOverlappingDuration(USER_ID, from, to)
        then:
            1 * shiftSpringDataRepository.findByUserIdAndActiveToGreaterThanEqualAndActiveFromLessThanEqual(USER_ID, from, to) >> [shiftEntity]
            1 * shiftDbMapper.mapToShift(shiftEntity) >> newShift
            noExceptionThrown()
    }

    def "verify find shift by user id and shop id within period"() {
        given:
            Instant from = TOMORROW
            Instant to = TOMORROW + Duration.ofHours(2)
            UserEntity userEntity = getSavedUserEntity()
            ShopEntity shopEntity = getSavedShopEntity()
            Shift newShift = new Shift(null, from, to, USER_ID, SHOP_ID)
            ShiftEntity shiftEntity = getShiftEntityToBeCreated(newShift, userEntity, shopEntity)
        when:
            shiftRepository.findShiftByUserIdAndShopIdWithin(USER_ID, SHOP_ID, from, to)
        then:
            1 * shiftSpringDataRepository.findByUserIdAndShopIdAndActiveToGreaterThanAndActiveFromLessThan(USER_ID, SHOP_ID, from, to) >> [shiftEntity]
            1 * shiftDbMapper.mapToShift(shiftEntity) >> newShift
            noExceptionThrown()
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

    private ShiftEntity getShiftEntityToBeCreated(Shift shift, UserEntity userEntity, ShopEntity shopEntity) {
        ShiftEntity shiftEntity = new ShiftEntity()
        shiftEntity.setActiveFrom(shift.activeFrom())
        shiftEntity.setActiveTo(shift.activeTo())
        shiftEntity.setUser(userEntity)
        shiftEntity.setShop(shopEntity)

        return shiftEntity
    }

}
