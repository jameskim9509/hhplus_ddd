package com.hhplus.ddd.infrastructure.core;

import com.hhplus.ddd.domain.lecture.LectureEntity;
import com.hhplus.ddd.domain.lecture.apply.ApplyEntity;
import com.hhplus.ddd.domain.user.UserEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplyJpaRepository extends JpaRepository<ApplyEntity, Long> {
    @EntityGraph(attributePaths = {"lecture"})
    List<ApplyEntity> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from ApplyEntity a where a.user=:user and a.lecture=:lecture")
    Optional<ApplyEntity> findByUserIdAndLectureIdWithLock(
            @Param("user") UserEntity user, @Param("lecture") LectureEntity lecture
    );

}