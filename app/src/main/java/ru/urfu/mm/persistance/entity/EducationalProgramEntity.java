package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "educational_programs")
public class EducationalProgramEntity {
    @Id
    @Column
    private UUID id;
    @Column
    private String name;
    @Column
    private String trainingDirection;
    @OneToMany
    private List<GroupEntity> groups;

    public EducationalProgramEntity() {
    }

    public EducationalProgramEntity(UUID id, String name, String trainingDirection, List<GroupEntity> groups) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.groups = groups;
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

    public List<GroupEntity> getGroups() {
        return Collections.unmodifiableList(groups);
    }
}
