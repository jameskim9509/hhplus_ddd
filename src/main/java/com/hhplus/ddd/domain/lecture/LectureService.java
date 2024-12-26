package com.hhplus.ddd.domain.lecture;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    public static Long maxCapacity = 30L;

    public LectureEntity getByIdWithLock(Long lectureId)
    {
        return lectureRepository.getByIdWithLock(lectureId).get();
    }

    public LectureEntity save(LectureEntity lecture)
    {
        return lectureRepository.save(lecture);
    }

    public List<LectureEntity> getByDate(LocalDate date)
    {
        return lectureRepository.getByDate(date).stream()
                .filter(lecture -> lecture.getApplicantCount() < maxCapacity)
                .toList();
    }
}