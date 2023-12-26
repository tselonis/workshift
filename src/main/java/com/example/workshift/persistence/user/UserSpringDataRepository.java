package com.example.workshift.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSpringDataRepository extends JpaRepository<UserEntity, Long> {
}