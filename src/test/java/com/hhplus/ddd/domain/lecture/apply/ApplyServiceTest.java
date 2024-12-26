package com.hhplus.ddd.domain.lecture.apply;

import com.hhplus.ddd.controller.error.LectureApplyException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ApplyServiceTest {
    @Mock
    private ApplyRepository applyRepository;
    @InjectMocks
    private ApplyService applyService;

    @DisplayName("신청한 특강 목록 없음")
    @Test
    void 신청한_특강목록_없음()
    {
        // given
        Mockito.doReturn(List.of()).when(applyRepository).getByUserIdEager(1L);

        // when, then
        Assertions.assertThatThrownBy(
                        () -> applyService.getByUserIdEager(1L)
                )
                .isInstanceOf(LectureApplyException.class)
                .hasMessage("신청한 강의 목록이 없습니다.");
    }
}