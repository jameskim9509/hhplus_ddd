package com.hhplus.ddd.domain.lecture;

import com.hhplus.ddd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Table(name = "lecture")
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LectureEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lectureName;

    @Temporal(TemporalType.DATE)
    private LocalDate lectureDate;

    private String instructor;

    @Column(name="category")
    @Enumerated(EnumType.STRING)
    private LectureCategory lectureCategory;

    private Long applicantCount;

    public void increaseApplicantCount()
    {
        applicantCount++;
    }
}
