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
    /**
     * Направление подготовки
     */
    private String trainingDirection;
    /**
     * Список студентов, которые учатся на данном направлении.
     */
    private List<Group> groups = new ArrayList<>();

    public Program() {
    }

    public Program(UUID id, String name, String trainingDirection) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
    }

    public Program(
            UUID id,
            String name,
            String trainingDirection,
            List<Group> groups) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.groups = groups;
    }

    public Program(String name, String trainingDirection) {
        this.name = name;
        this.trainingDirection = trainingDirection;
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

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
