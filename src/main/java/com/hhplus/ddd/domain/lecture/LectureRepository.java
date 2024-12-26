package com.hhplus.ddd.domain.lecture;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository {
    public Optional<LectureEntity> getByIdWithLock(Long lectureId);
    public LectureEntity save(LectureEntity lecture);

    public List<LectureEntity> getByDate(LocalDate date);
}

