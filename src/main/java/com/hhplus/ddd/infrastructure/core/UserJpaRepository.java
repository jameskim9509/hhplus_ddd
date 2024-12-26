package com.hhplus.ddd.infrastructure.core;

import com.hhplus.ddd.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
