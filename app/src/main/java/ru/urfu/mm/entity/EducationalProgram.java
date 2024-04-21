package ru.urfu.mm.entity;

import jakarta.persistence.*;

import java.util.*;

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
    private int firstRecommendedCredits;
    @Column
    private int secondRecommendedCredits;
    @Column
    private int thirdRecommendedCredits;
    @Column
    private int fourthRecommendedCredits;

    public EducationalProgram() {
    }

    public EducationalProgram(UUID id, String name, String trainingDirection) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
    }

    public EducationalProgram(String name, String trainingDirection) {
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
