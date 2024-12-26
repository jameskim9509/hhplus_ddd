package com.hhplus.ddd.infrastructure.core;

import com.hhplus.ddd.domain.lecture.LectureEntity;
import com.hhplus.ddd.domain.lecture.apply.ApplyEntity;
import com.hhplus.ddd.domain.lecture.apply.ApplyRepository;
import com.hhplus.ddd.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ApplyRepositoryImpl implements ApplyRepository {
    private final ApplyJpaRepository applyJpaRepository;

    @Override
    public ApplyEntity save(UserEntity user, LectureEntity lecture)
    {
        return applyJpaRepository.save(
                ApplyEntity.builder()
                        .user(user)
                        .lecture(lecture)
                        .build()
        );
    }

    @Override
    public List<ApplyEntity> getByUserIdEager(Long userId)
    {
        return applyJpaRepository.findByUserId(userId);
    }

    @Override
    public Optional<ApplyEntity> getByUserAndLectureWithLock(UserEntity user, LectureEntity lecture)
    {
        return applyJpaRepository.findByUserIdAndLectureIdWithLock(user, lecture);
    }
}
