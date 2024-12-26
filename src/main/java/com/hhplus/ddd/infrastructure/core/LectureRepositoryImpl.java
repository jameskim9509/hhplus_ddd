package com.hhplus.ddd.infrastructure.core;

import com.hhplus.ddd.domain.lecture.LectureEntity;
import com.hhplus.ddd.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {
    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public LectureEntity save(LectureEntity lecture)
    {
        return lectureJpaRepository.save(lecture);
    }

    @Override
    public List<LectureEntity> getByDate(LocalDate date)
    {
        return lectureJpaRepository.findByLectureDate(date);
    }

    @Override
    public Optional<LectureEntity> getByIdWithLock(Long lectureId)
    {
        return lectureJpaRepository.findLectureWithLock(lectureId);
    }
}
