package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "base_syllabus")
public class BaseSyllabusEntity {
    @Id
    private UUID id;
    @OneToOne
    private BaseSemesterPlanEntity firstSemesterPlan;
    @OneToOne
    private BaseSemesterPlanEntity secondSemesterPlan;
    @OneToOne
    private BaseSemesterPlanEntity thirdSemesterPlan;
    @OneToOne
    private BaseSemesterPlanEntity fourthSemesterPlan;

    public BaseSyllabusEntity() {
    }

    public BaseSyllabusEntity(
            UUID id,
            BaseSemesterPlanEntity firstSemesterPlan,
            BaseSemesterPlanEntity secondSemesterPlan,
            BaseSemesterPlanEntity thirdSemesterPlan,
            BaseSemesterPlanEntity fourthSemesterPlan
    ) {
        this.id = id;
        this.firstSemesterPlan = firstSemesterPlan;
        this.secondSemesterPlan = secondSemesterPlan;
        this.thirdSemesterPlan = thirdSemesterPlan;
        this.fourthSemesterPlan = fourthSemesterPlan;
    }
}
