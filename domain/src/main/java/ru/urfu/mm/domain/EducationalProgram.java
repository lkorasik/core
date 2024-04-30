package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Образовательная программа
 */
public class EducationalProgram {
    /**
     * Идентификатор образовательной программы
     */
    private UUID id;
    /**
     * Название образовательной программы
     */
    private String name;
    /**
     * Назавние направления подготовки
     */
    private String trainingDirection;
    /**
     * Список академических групп
     */
    private List<AcademicGroup> academicGroups;
    /**
     * Список учбеных планов
     */
    private List<Syllabus> syllabi;

    public EducationalProgram(UUID id, String name, String trainingDirection) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.academicGroups = new ArrayList<>();
        this.syllabi = new ArrayList<>();
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

    public List<Syllabus> getSyllabi() {
        return syllabi;
    }

    public void setGroups(List<AcademicGroup> academicGroups) {
        this.academicGroups = academicGroups;
    }
}
