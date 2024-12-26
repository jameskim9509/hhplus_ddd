package com.hhplus.ddd.domain.lecture.apply;

import com.hhplus.ddd.domain.common.BaseEntity;
import com.hhplus.ddd.domain.lecture.LectureEntity;
import com.hhplus.ddd.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "apply")
@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ApplyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private LectureEntity lecture;
}
