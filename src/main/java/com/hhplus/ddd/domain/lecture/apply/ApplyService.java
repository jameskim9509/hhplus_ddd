package com.hhplus.ddd.domain.lecture.apply;

import com.hhplus.ddd.controller.error.ErrorCode;
import com.hhplus.ddd.controller.error.LectureApplyException;
import com.hhplus.ddd.domain.lecture.LectureEntity;
import com.hhplus.ddd.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;

    public ApplyEntity save(UserEntity user, LectureEntity lecture)
    {
        return applyRepository.save(user, lecture);
    }

    public Boolean isApplyExist(UserEntity user, LectureEntity lecture)
    {
        return applyRepository.getByUserAndLectureWithLock(user, lecture).isPresent();
    }

    public List<ApplyEntity> getByUserIdEager(Long userId)
    {
        return applyRepository.getByUserIdEager(userId);
    }
}