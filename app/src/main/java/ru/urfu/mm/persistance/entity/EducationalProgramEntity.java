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

    public EducationalProgramEntity() {
    }

    public EducationalProgramEntity(UUID id, String name, String trainingDirection) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
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
