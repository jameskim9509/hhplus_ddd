package com.hhplus.ddd.domain.lecture;

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
}