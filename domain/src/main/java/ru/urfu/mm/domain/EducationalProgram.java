package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Образовательная программа
 */
public class EducationalProgram {
    /**
     * Идентификатор направления
     */
    private UUID id;
    /**
     * Название направления
     */
    private String name;
    /**
     * Направление подготовки
     */
    private String trainingDirection;
    /**
     * Список академических групп
     */
    private List<AcademicGroup> academicGroups = new ArrayList<>();
    /**
     * Список учбеных планов
     */
    private List<StudyPlan> studyPlans = new ArrayList<>();

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

    public List<AcademicGroup> getGroups() {
        return academicGroups;
    }

    public String getTrainingDirection() {
        return trainingDirection;
    }

    public List<StudyPlan> getStudyPlans() {
        return studyPlans;
    }

    public void setGroups(List<AcademicGroup> academicGroups) {
        this.academicGroups = academicGroups;
    }
}
