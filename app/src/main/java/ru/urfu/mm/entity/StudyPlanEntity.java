package ru.urfu.mm.entity;

import jakarta.persistence.*;

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
    @ManyToOne
    @JoinColumn
    private EducationalProgram program;

    public StudyPlanEntity() {
    }

    public StudyPlanEntity(
            UUID id,
            SemesterPlanEntity firstSemester,
            SemesterPlanEntity secondSemester,
            SemesterPlanEntity thirdSemester,
            SemesterPlanEntity fourthSemester,
            EducationalProgram program) {
        this.id = id;
        this.program = program;
        this.firstSemester = firstSemester;
        this.secondSemester = secondSemester;
        this.thirdSemester = thirdSemester;
        this.fourthSemester = fourthSemester;
    }
}
