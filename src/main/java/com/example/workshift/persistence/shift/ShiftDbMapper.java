package com.example.workshift.persistence.shift;

import com.example.workshift.business.shift.Shift;
import com.example.workshift.persistence.shop.ShopEntity;
import com.example.workshift.persistence.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
class ShiftDbMapper {

    ShiftEntity mapToShiftEntityForCreate(final Shift shift, final UserEntity userEntity, final ShopEntity shopEntity) {
        final ShiftEntity shiftEntity = new ShiftEntity();
        shiftEntity.setActiveFrom(shift.activeFrom());
        shiftEntity.setActiveTo(shift.activeTo());
        shiftEntity.setUser(userEntity);
        shiftEntity.setShop(shopEntity);

        return shiftEntity;
    }

    Shift mapToShift(final ShiftEntity shiftEntity) {
        return new Shift(
                shiftEntity.getId(),
                shiftEntity.getActiveFrom(),
                shiftEntity.getActiveTo(),
                shiftEntity.getUser().getId(),
                shiftEntity.getShop().getId()
        );
    }
}
