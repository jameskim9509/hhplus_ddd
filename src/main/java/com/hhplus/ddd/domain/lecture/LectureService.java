package com.hhplus.ddd.domain.lecture;

import com.hhplus.ddd.controller.error.ErrorCode;
import com.hhplus.ddd.controller.error.LectureApplyException;
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
        return lectureRepository.getByIdWithLock(lectureId)
                .orElseThrow(()-> new LectureApplyException(ErrorCode.NOT_FOUND_LECTURE));
    }

    public LectureEntity save(LectureEntity lecture)
    {
        return lectureRepository.save(lecture);
    }

    // query vs application 단에서 처리
    public List<LectureEntity> getByDate(LocalDate date)
    {
        List<LectureEntity> lectureEntities = lectureRepository.getByDate(date).stream()
                .filter(lecture -> lecture.getApplicantCount() < maxCapacity)
                .toList();

        if(lectureEntities.isEmpty())
            throw new LectureApplyException(ErrorCode.NOT_FOUND_LECTURE_LIST);
        return lectureEntities;
    }
}
