package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "base_semester_plans")
public class BaseSemesterPlanEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private SemesterEntity semesterEntity;
//    private final List<Course> requiredCourses;
//    private final List<Course> availableCourses;
//    private final List<Course> scienceWorks;


    public BaseSemesterPlanEntity() {
    }

    public BaseSemesterPlanEntity(UUID id, SemesterEntity semesterEntity) {
        this.id = id;
        this.semesterEntity = semesterEntity;
    }
}
