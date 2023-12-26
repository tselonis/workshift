package com.example.workshift.persistence.shift;

import com.example.workshift.business.shift.Shift;
import com.example.workshift.business.shift.ShiftRepository;
import com.example.workshift.persistence.exception.ShopNotFoundException;
import com.example.workshift.persistence.exception.UserNotFoundException;
import com.example.workshift.persistence.shop.ShopEntity;
import com.example.workshift.persistence.shop.ShopSpringDataRepository;
import com.example.workshift.persistence.user.UserEntity;
import com.example.workshift.persistence.user.UserSpringDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@AllArgsConstructor
class H2ShiftRepository implements ShiftRepository {

    private ShiftSpringDataRepository shiftSpringDataRepository;
    private UserSpringDataRepository userSpringDataRepository;
    private ShopSpringDataRepository shopSpringDataRepository;
    private ShiftDbMapper shiftDbMapper;

    @Override
    public Shift createShift(final Shift shift) {
        final UserEntity userEntity = userSpringDataRepository.findById(shift.userId()).orElseThrow(UserNotFoundException::new);
        final ShopEntity shopEntity = shopSpringDataRepository.findById(shift.shopId()).orElseThrow(ShopNotFoundException::new);
        final ShiftEntity shiftEntity = shiftDbMapper.mapToShiftEntityForCreate(shift, userEntity, shopEntity);
        final ShiftEntity savedShift = shiftSpringDataRepository.save(shiftEntity);

        return shiftDbMapper.mapToShift(savedShift);
    }

    @Override
    public List<Shift> findShiftByUserIdAndOverlappingDuration(final Long userId, final Instant from, final Instant to) {
        final List<ShiftEntity> shiftsFound = shiftSpringDataRepository.findByUserIdAndActiveToGreaterThanEqualAndActiveFromLessThanEqual(userId, from, to);
        return shiftsFound.stream().map(shiftDbMapper::mapToShift).toList();
    }

    @Override
    public List<Shift> findShiftByUserIdAndShopIdWithin(Long userId, Long shopId, Instant from, Instant to) {
        final List<ShiftEntity> shiftsFound = shiftSpringDataRepository.findByUserIdAndShopIdAndActiveToGreaterThanAndActiveFromLessThan(
                userId,
                shopId,
                from,
                to
        );
        return shiftsFound.stream().map(shiftDbMapper::mapToShift).toList();
    }
}
