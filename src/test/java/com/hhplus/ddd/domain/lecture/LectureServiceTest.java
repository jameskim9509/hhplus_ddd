package com.hhplus.ddd.domain.lecture;

import com.hhplus.ddd.controller.error.LectureApplyException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @DisplayName("날찌별 조회 성공")
    @Test
    void getByDate() {
        // given
        Mockito.doReturn(
                List.of(
                        LectureEntity.builder()
                                .applicantCount(LectureService.maxCapacity)
                                .id(1L)
                                .build(),
                        LectureEntity.builder()
                                .applicantCount(29L)
                                .id(2L)
                                .build(),
                        LectureEntity.builder()
                                .applicantCount(0L)
                                .id(3L)
                                .build()
                )
        ).when(lectureRepository).getByDate(LocalDate.now());

        // when
        List<LectureEntity> lectureEntities = lectureService.getByDate(LocalDate.now());

        // then
        assertThat(lectureEntities.stream().anyMatch(l -> l.getId() == 1L)).isEqualTo(false);
        assertThat(lectureEntities.stream().anyMatch(l -> l.getId() == 2L)).isEqualTo(true);
        assertThat(lectureEntities.stream().anyMatch(l -> l.getId() == 3L)).isEqualTo(true);
    }

    @DisplayName("존재하지 않는 강의 오류 발생")
    @Test
    void 존재하지_않는_강의입니다()
    {
        // given
        Mockito.doReturn(Optional.empty()).when(lectureRepository).getByIdWithLock(1L);

        // when, then
        Assertions.assertThatThrownBy(
                        () -> lectureService.getByIdWithLock(1L)
                )
                .isInstanceOf(LectureApplyException.class)
                .hasMessage("존재하지 않는 강의입니다.");
    }

    @DisplayName("해당 날짜에 강의 목록 없음 오류 발생")
    @Test
    void 해당_날짜에_강의가_없습니다()
    {
        // given
        Mockito.doReturn(List.of()).when(lectureRepository).getByDate(LocalDate.now());

        // when, then
        Assertions.assertThatThrownBy(
                        () -> lectureService.getByDate(LocalDate.now())
                ).isInstanceOf(LectureApplyException.class)
                .hasMessage("해당 날짜에 강의가 존재하지 않습니다.");
    }

}