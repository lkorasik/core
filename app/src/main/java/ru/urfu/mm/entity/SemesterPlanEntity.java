package ru.urfu.mm.entity;

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
    private Semester semester;
    @Column
    private int recommendedCredits;
    @ManyToMany
    List<Module> requiredModules;
    @ManyToMany
    List<Module> specialModules;
    @ManyToMany
    List<Module> scienceWork;

    public SemesterPlanEntity() {
    }

    public SemesterPlanEntity(UUID id, Semester semester, int recommendedCredits) {
        this.id = id;
        this.semester = semester;
        this.recommendedCredits = recommendedCredits;
    }
}
