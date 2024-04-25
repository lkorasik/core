package ru.urfu.mm.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class StudyPlanEntity {
    @Id
    private UUID id;
    @ManyToOne
    private SemesterPlanEntity firstSemester;
    @ManyToOne
    private SemesterPlanEntity secondSemester;
    @ManyToOne
    private SemesterPlanEntity thirdSemester;
    @ManyToOne
    private SemesterPlanEntity fourthSemester;
    @ManyToOne
    @JoinColumn(name="educational_programs_id")
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

    public void setProgram(EducationalProgram program) {
        this.program = program;
    }

    public UUID getId() {
        return id;
    }

    public SemesterPlanEntity getFirstSemester() {
        return firstSemester;
    }

    public SemesterPlanEntity getSecondSemester() {
        return secondSemester;
    }

    public SemesterPlanEntity getThirdSemester() {
        return thirdSemester;
    }

    public SemesterPlanEntity getFourthSemester() {
        return fourthSemester;
    }

    public EducationalProgram getProgram() {
        return program;
    }
}
