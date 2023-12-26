package com.example.workshift.persistence.shift;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface ShiftSpringDataRepository extends JpaRepository<ShiftEntity, Long> {

    List<ShiftEntity> findByUserIdAndActiveToGreaterThanEqualAndActiveFromLessThanEqual(Long userId, Instant activeFrom, Instant activeTo);

    List<ShiftEntity> findByUserIdAndShopIdAndActiveToGreaterThanAndActiveFromLessThan(Long userId, Long shopId, Instant from, Instant to);
}
