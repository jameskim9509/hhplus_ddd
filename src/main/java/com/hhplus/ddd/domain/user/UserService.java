package com.hhplus.ddd.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity getById(Long userId)
    {
        return userRepository.getById(userId).get();
    }
}
