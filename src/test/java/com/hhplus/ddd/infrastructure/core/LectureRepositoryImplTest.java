package com.hhplus.ddd.infrastructure.core;

import com.hhplus.ddd.domain.lecture.LectureEntity;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

@AutoConfigureTestDatabase(
        connection= EmbeddedDatabaseConnection.H2,
        replace = AutoConfigureTestDatabase.Replace.ANY
)
@TestPropertySource("classpath:application-test.properties")
@DataJpaTest
class LectureRepositoryImplTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    LectureJpaRepository lectureJpaRepository;

    LectureRepositoryImpl lectureRepository;

    @BeforeEach
    void setUp()
    {
        lectureRepository = new LectureRepositoryImpl(lectureJpaRepository);
    }

    @Test
    void save() {
        // given, when
        LectureEntity lectureEntity = lectureRepository.save(
                LectureEntity.builder()
                        .lectureName("lecture1")
                        .build()
        );

        entityManager.flush();

        // then
        Assertions.assertThat(lectureJpaRepository.findById(lectureEntity.getId()).get().getLectureName())
                .isEqualTo("lecture1");

    }

    @Test
    void getByDate() {
        // given
        lectureJpaRepository.save(
                LectureEntity.builder()
                        .lectureName("lecture1")
                        .lectureDate(LocalDate.now())
                        .build()
        );

        entityManager.flush();

        // when, then
        Assertions.assertThat(
                lectureRepository.getByDate(LocalDate.now()).stream()
                        .anyMatch(l -> "lecture1".equals(l.getLectureName()))
        ).isEqualTo(true);
    }

    @Test
    void getByIdWithLock() {
        // when
        Assertions.assertThat(lectureRepository.getByIdWithLock(1L).get().getLectureName())
                .isEqualTo("c lang");
    }
}