package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Направление. Образовательная программа.
 */
public class Program {
    /**
     * Идентификатор направления
     */
    private UUID id;
    /**
     * Название направления
     */
    private String name;
    private String trainingDirection;
    private String semesterIdToRequiredCreditsCount;
    /**
     * Список студентов, которые учатся на данном направлении.
     */
    private List<Group> groups = new ArrayList<>();

    public Program() {
    }

    public Program(UUID id, String name, String trainingDirection, String semesterIdToRequiredCreditsCount) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.semesterIdToRequiredCreditsCount = semesterIdToRequiredCreditsCount;
    }

    public Program(String name, String trainingDirection, String semesterIdToRequiredCreditsCount) {
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

    public List<Group> getGroups() {
        return groups;
    }

    public String getTrainingDirection() {
        return trainingDirection;
    }

    public String getSemesterIdToRequiredCreditsCount() {
        return semesterIdToRequiredCreditsCount;
    }
}
