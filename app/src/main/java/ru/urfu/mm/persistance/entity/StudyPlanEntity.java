package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "study_plans")
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
    @JoinColumn(name="programs_id")
    private ProgramEntity program;

    public StudyPlanEntity() {
    }

    public StudyPlanEntity(
            UUID id,
            SemesterPlanEntity firstSemester,
            SemesterPlanEntity secondSemester,
            SemesterPlanEntity thirdSemester,
            SemesterPlanEntity fourthSemester,
            ProgramEntity program) {
        this.id = id;
        this.program = program;
        this.firstSemester = firstSemester;
        this.secondSemester = secondSemester;
        this.thirdSemester = thirdSemester;
        this.fourthSemester = fourthSemester;
    }

    public void setProgram(ProgramEntity program) {
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

    public ProgramEntity getProgram() {
        return program;
    }
}
