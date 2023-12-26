package com.example.workshift.persistence.shop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopSpringDataRepository extends JpaRepository<ShopEntity, Long> {
}
