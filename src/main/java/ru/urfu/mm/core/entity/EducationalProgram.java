package ru.urfu.mm.core.entity;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "educational_programs")
public class EducationalProgram {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private String name;
    @Column
    private String trainingDirection;
    @Column
    private String semesterIdToRequiredCreditsCount;

    public EducationalProgram() {
    }

    public EducationalProgram(UUID id, String name, String trainingDirection, String semesterIdToRequiredCreditsCount) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.semesterIdToRequiredCreditsCount = semesterIdToRequiredCreditsCount;
    }

    public EducationalProgram(String name, String trainingDirection, String semesterIdToRequiredCreditsCount) {
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.semesterIdToRequiredCreditsCount = semesterIdToRequiredCreditsCount;
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

    public String getSemesterIdToRequiredCreditsCount() {
        return semesterIdToRequiredCreditsCount;
    }
}
