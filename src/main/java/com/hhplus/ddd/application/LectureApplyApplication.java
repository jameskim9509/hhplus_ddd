package com.hhplus.ddd.application;

import com.hhplus.ddd.controller.error.ErrorCode;
import com.hhplus.ddd.controller.error.LectureApplyException;
import com.hhplus.ddd.domain.lecture.LectureEntity;
import com.hhplus.ddd.domain.lecture.LectureService;
import com.hhplus.ddd.domain.lecture.apply.ApplyEntity;
import com.hhplus.ddd.domain.lecture.apply.ApplyService;
import com.hhplus.ddd.domain.user.UserEntity;
import com.hhplus.ddd.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LectureApplyApplication {
    private final LectureService lectureService;
    private final UserService userService;
    private final ApplyService applyService;

    @Transactional
    public ApplyEntity applyLecture(Long userId, Long lectureId){
        UserEntity findUser = userService.getById(userId);
        LectureEntity findLecture = lectureService.getByIdWithLock(lectureId);

        findLecture.increaseApplicantCount();
        lectureService.save(findLecture);
        return applyService.save(findUser, findLecture);
    }

    public List<LectureEntity> getLecturesByDate(LocalDate date){
        return lectureService.getByDate(date);
    }

    public List<LectureEntity> getCompletedByUserId(Long userId)
    {
        return applyService.getByUserIdEager(userId).stream()
                .map(ApplyEntity::getLecture)
                .toList();
    }
}