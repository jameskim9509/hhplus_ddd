package com.hhplus.ddd.application;

import com.hhplus.ddd.controller.error.LectureApplyException;
import com.hhplus.ddd.domain.lecture.LectureEntity;
import com.hhplus.ddd.domain.lecture.LectureService;
import com.hhplus.ddd.domain.lecture.apply.ApplyEntity;
import com.hhplus.ddd.domain.lecture.apply.ApplyService;
import com.hhplus.ddd.domain.user.UserEntity;
import com.hhplus.ddd.domain.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LectureApplyApplicationUnitTest {
    @Mock
    private LectureService lectureService;

    @Mock
    private UserService userService;

    @Mock
    private ApplyService applyService;

    @InjectMocks
    private LectureApplyApplication lectureApplyApplication;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("수강 신청 성공")
    @Test
    void applyLecture() {
        // given
        Mockito.doReturn(
                UserEntity.builder()
                        .id(1L)
                        .username("james")
                        .build()
        ).when(userService).getById(1L);
        Mockito.doReturn(
                LectureEntity.builder()
                        .id(1L)
                        .applicantCount(1L)
                        .lectureName("c lang")
                        .lectureDate(LocalDate.now())
                        .build()
        ).when(lectureService).getByIdWithLock(1L);

        ArgumentCaptor<UserEntity> userEntityCaptor =
                ArgumentCaptor.forClass(UserEntity.class);
        ArgumentCaptor<LectureEntity> lectureEntityCaptor =
                ArgumentCaptor.forClass(LectureEntity.class);

        // when
        lectureApplyApplication.applyLecture(1L, 1L);
        Mockito.verify(applyService).save(userEntityCaptor.capture(), lectureEntityCaptor.capture());

        UserEntity capturedUser = userEntityCaptor.getValue();
        LectureEntity capturedLecture = lectureEntityCaptor.getValue();

        // then
        assertThat(capturedUser.getId()).isEqualTo(1L);
        assertThat(capturedLecture.getLectureName()).isEqualTo("c lang");
        assertThat(capturedLecture.getApplicantCount()).isEqualTo(2L);
    }

    @DisplayName("수강 신청된 목록 조회 성공")
    @Test
    void getCompletedByUserId() {
        // given
        Mockito.doReturn(
                List.of(
                        ApplyEntity.builder()
                                .id(1L)
                                .user(UserEntity.builder().id(1L).build())
                                .lecture(LectureEntity.builder().id(1L).build())
                                .build(),
                        ApplyEntity.builder()
                                .id(2L)
                                .user(UserEntity.builder().id(1L).build())
                                .lecture(LectureEntity.builder().id(2L).build())
                                .build()
                )
        ).when(applyService).getByUserIdEager(1L);

        // when
        List<LectureEntity> lectureEntities = lectureApplyApplication.getCompletedByUserId(1L);

        // then
        assertThat(lectureEntities.stream().anyMatch(l -> l.getId() == 1L)).isEqualTo(true);
        assertThat(lectureEntities.stream().anyMatch(l -> l.getId() == 2L)).isEqualTo(true);
    }
}