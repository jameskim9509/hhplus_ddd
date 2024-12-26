package com.hhplus.ddd.domain.user;

import com.hhplus.ddd.controller.error.LectureApplyException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @DisplayName("존재하지 않는 사용자 오류 발생")
    @Test
    void 존재하지_않는_사용자입니다()
    {
        // given
        Mockito.doReturn(Optional.empty()).when(userRepository).getById(1L);

        // when, then
        Assertions.assertThatThrownBy(
                        () -> userService.getById(1L)
                )
                .isInstanceOf(LectureApplyException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }
}