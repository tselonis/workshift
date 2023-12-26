package com.example.workshift.business.shift;

import java.time.Instant;
import java.util.List;

public interface ShiftRepository {
    Shift createShift(Shift shift);

    List<Shift> findShiftByUserIdAndOverlappingDuration(Long userId, Instant from, Instant to);

    List<Shift> findShiftByUserIdAndShopIdWithin(Long userId, Long shopId, Instant from, Instant to);

}
