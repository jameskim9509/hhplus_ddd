package com.hhplus.ddd.domain.user;

import com.hhplus.ddd.controller.error.ErrorCode;
import com.hhplus.ddd.controller.error.LectureApplyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity getById(Long userId)
    {
        return userRepository.getById(userId)
                .orElseThrow(() -> new LectureApplyException(ErrorCode.NOT_FOUND_USER));
    }
}
