package com.hhplus.ddd.infrastructure.core;

import com.hhplus.ddd.domain.user.UserEntity;
import com.hhplus.ddd.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<UserEntity> getById(Long userId) {
        return userJpaRepository.findById(userId);
    }
}

