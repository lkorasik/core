package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
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
    List<SpecialCourse> requiredCoursesEntities = new ArrayList<>();
    @ManyToMany
    List<SpecialCourse> specialCoursesEntities = new ArrayList<>();
    @ManyToMany
    List<SpecialCourse> scienceWork = new ArrayList<>();

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

    public List<SpecialCourse> getRequiredModules() {
        return requiredCoursesEntities;
    }

    public List<SpecialCourse> getSpecialModules() {
        return specialCoursesEntities;
    }

    public List<SpecialCourse> getScienceWork() {
        return scienceWork;
    }
}
