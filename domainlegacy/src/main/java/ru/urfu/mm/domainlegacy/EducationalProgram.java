package ru.urfu.mm.domainlegacy;

import java.util.UUID;

public class EducationalProgram {
    private UUID id;
    private String name;
    private String trainingDirection;
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
