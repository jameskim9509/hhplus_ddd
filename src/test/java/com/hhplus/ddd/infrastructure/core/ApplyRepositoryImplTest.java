package com.hhplus.ddd.infrastructure.core;

import com.hhplus.ddd.domain.lecture.LectureEntity;
import com.hhplus.ddd.domain.lecture.apply.ApplyEntity;
import com.hhplus.ddd.domain.user.UserEntity;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@AutoConfigureTestDatabase(
        connection= EmbeddedDatabaseConnection.H2,
        replace = AutoConfigureTestDatabase.Replace.ANY
)
@TestPropertySource("classpath:application-test.properties")
@DataJpaTest
class ApplyRepositoryImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ApplyJpaRepository applyJpaRepository;


    private ApplyRepositoryImpl applyRepository;

    @BeforeEach
    void setUp()
    {
        applyRepository = new ApplyRepositoryImpl(applyJpaRepository);
    }

    @Test
    void save() {
        // given
        applyRepository.save(
                UserEntity.builder()
                        .id(1L)
                        .username("aimy")
                        .build(),
                LectureEntity.builder()
                        .id(1L)
                        .lectureName("c lang")
                        .build()
        );

        entityManager.flush();

        // when
        ApplyEntity findApplyEntity =
                applyJpaRepository.findByUserIdAndLectureIdWithLock(
                        UserEntity.builder().id(1L).build(),
                        LectureEntity.builder().id(1L).build()
                ).get();

        // then
        Assertions.assertThat(findApplyEntity.getUser().getUsername()).isEqualTo("aimy");
        Assertions.assertThat(findApplyEntity.getLecture().getLectureName()).isEqualTo("c lang");
    }

    @Test
    void getByUserIdEager() {
        // given
        applyRepository.save(
                UserEntity.builder()
                        .id(1L)
                        .build(),
                LectureEntity.builder()
                        .id(1L)
                        .build()
        );
        applyRepository.save(
                UserEntity.builder()
                        .id(1L)
                        .build(),
                LectureEntity.builder()
                        .id(2L)
                        .build()
        );

        entityManager.flush();

        // when
        List<ApplyEntity> applyEntities = applyJpaRepository.findByUserId(1L);

        // then
        Assertions.assertThat(applyEntities.stream().anyMatch(a -> a.getLecture().getId() == 1L))
                .isEqualTo(true);
        Assertions.assertThat(applyEntities.stream().anyMatch(a -> a.getLecture().getId() == 2L))
                .isEqualTo(true);
    }

    @Test
    void getByUserIdAndLectureId() {
        // given
        applyRepository.save(
                UserEntity.builder()
                        .id(1L)
                        .build(),
                LectureEntity.builder()
                        .id(1L)
                        .build()
        );

        entityManager.flush();

        // when, then
        Assertions.assertThat(
                        applyRepository.getByUserAndLectureWithLock(
                                UserEntity.builder().id(1L).build(),
                                LectureEntity.builder().id(1L).build())
                )
                .isNotEmpty();
    }
}