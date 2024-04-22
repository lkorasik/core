package ru.urfu.mm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table
public class StudyPlanEntity {
    @Id
    private UUID id;
    @OneToOne
    private SemesterPlanEntity firstSemester;
    @OneToOne
    private SemesterPlanEntity secondSemester;
    @OneToOne
    private SemesterPlanEntity thirdSemester;
    @OneToOne
    private SemesterPlanEntity fourthSemester;

    public StudyPlanEntity() {
    }

    public StudyPlanEntity(
            UUID id,
            SemesterPlanEntity firstSemester,
            SemesterPlanEntity secondSemester,
            SemesterPlanEntity thirdSemester,
            SemesterPlanEntity fourthSemester) {
        this.id = id;
        this.firstSemester = firstSemester;
        this.secondSemester = secondSemester;
        this.thirdSemester = thirdSemester;
        this.fourthSemester = fourthSemester;
    }
}
