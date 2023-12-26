package com.example.workshift.it

import com.example.workshift.business.shift.ShiftService
import com.example.workshift.business.shop.ShopService
import com.example.workshift.business.user.UserService
import com.example.workshift.persistence.shift.ShiftSpringDataRepository
import com.example.workshift.persistence.shop.ShopSpringDataRepository
import com.example.workshift.persistence.user.UserSpringDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class BaseIntegrationSpec extends Specification {

    @Autowired
    UserService userService

    @Autowired
    ShopService shopService

    @Autowired
    ShiftService shiftService

    @Autowired
    UserSpringDataRepository userSpringDataRepository

    @Autowired
    ShopSpringDataRepository shopSpringDataRepository

    @Autowired
    ShiftSpringDataRepository shiftSpringDataRepository

    def setup() {
        shiftSpringDataRepository.deleteAll()
        userSpringDataRepository.deleteAll()
        shopSpringDataRepository.deleteAll()
    }
}
