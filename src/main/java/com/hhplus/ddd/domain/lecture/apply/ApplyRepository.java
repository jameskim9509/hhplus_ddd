package com.hhplus.ddd.domain.lecture.apply;

import com.hhplus.ddd.domain.lecture.LectureEntity;
import com.hhplus.ddd.domain.user.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ApplyRepository {
    public ApplyEntity save(UserEntity user, LectureEntity lecture);
    public List<ApplyEntity> getByUserIdEager(Long userId);
    public Optional<ApplyEntity> getByUserAndLectureWithLock(UserEntity userId, LectureEntity LectureId);
}
