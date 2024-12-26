package com.hhplus.ddd.domain.user;

import java.util.Optional;

public interface UserRepository {
    public Optional<UserEntity> getById(Long userId);
}