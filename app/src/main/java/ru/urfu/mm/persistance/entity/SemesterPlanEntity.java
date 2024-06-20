package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "semester_plans")
public class SemesterPlanEntity {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "semesters_id")
    private SemesterEntity semesterEntity;
    @Column
    private int recommendedCredits;
    @ManyToMany
    List<EducationalModuleEntity> requiredEducationalModuleEntities;
    @ManyToMany
    List<EducationalModuleEntity> specialEducationalModuleEntities;
    @ManyToMany
    List<EducationalModuleEntity> scienceWork;

    public SemesterPlanEntity() {
    }

    public SemesterPlanEntity(UUID id, SemesterEntity semesterEntity, int recommendedCredits) {
        this.id = id;
        this.semesterEntity = semesterEntity;
        this.recommendedCredits = recommendedCredits;
    }

    public UUID getId() {
        return id;
    }

    public SemesterEntity getSemester() {
        return semesterEntity;
    }

    public int getRecommendedCredits() {
        return recommendedCredits;
    }

    public List<EducationalModuleEntity> getRequiredModules() {
        return requiredEducationalModuleEntities;
    }

    public List<EducationalModuleEntity> getSpecialModules() {
        return specialEducationalModuleEntities;
    }

    public List<EducationalModuleEntity> getScienceWork() {
        return scienceWork;
    }
}
