package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "programs")
public class ProgramEntity {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private String name;
    @Column
    private String trainingDirection;
    @OneToMany
    @JoinColumn(name = "groups_id")
    private List<GroupEntity> groups;
    @OneToMany
    @JoinColumn(name = "study_plans_id")
    private List<StudyPlanEntity> studyPlans;

    public ProgramEntity() {
    }

    public ProgramEntity(UUID id, String name, String trainingDirection) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.groups = new ArrayList<>();
        this.studyPlans = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTrainingDirection() {
        return trainingDirection;
    }
}
