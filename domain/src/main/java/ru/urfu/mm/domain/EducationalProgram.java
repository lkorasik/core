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
    private final UUID id;
    /**
     * Название образовательной программы
     */
    private final String name;
    /**
     * Назавние направления подготовки
     */
    private final String trainingDirection;
    /**
     * Список академических групп
     */
    private final List<AcademicGroup> academicGroups;

    public EducationalProgram(UUID id, String name, String trainingDirection) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.academicGroups = new ArrayList<>();
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
