package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "base_semester_plans")
public class BaseSemesterPlanEntity {
    @Id
    private UUID id;
    @ManyToOne
    private SemesterEntity semesterEntity;
    @ManyToMany
    private List<SpecialCourse> requiredCourses;
    @ManyToMany
    private List<SpecialCourse> availableCourses;
    @ManyToMany
    private List<SpecialCourse> scienceWorks;


    public BaseSemesterPlanEntity() {
    }

    public BaseSemesterPlanEntity(UUID id, SemesterEntity semesterEntity) {
        this.id = id;
        this.semesterEntity = semesterEntity;
        this.requiredCourses = new ArrayList<>();
        this.availableCourses = new ArrayList<>();
        this.scienceWorks = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public SemesterEntity getSemesterEntity() {
        return semesterEntity;
    }

    public List<SpecialCourse> getRequiredCourses() {
        return Collections.unmodifiableList(requiredCourses);
    }

    public List<SpecialCourse> getAvailableCourses() {
        return Collections.unmodifiableList(availableCourses);
    }

    public List<SpecialCourse> getScienceWorks() {
        return Collections.unmodifiableList(scienceWorks);
    }
}
