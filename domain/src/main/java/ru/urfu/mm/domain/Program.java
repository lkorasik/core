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
    /**
     * Список студентов, которые учатся на данном направлении.
     */
    private List<Group> groups = new ArrayList<>();
    /**
     * Число рекомендуемых зеток для первого семестра
     */
    private int firstRecommendedCredits;
    /**
     * Число рекомендуемых зеток для второго семестра
     */
    private int secondRecommendedCredits;
    /**
     * Число рекомендуемых зекток для третьего семестра
     */
    private int thirdRecommendedCredits;
    /**
     * Число рекомендуемых зеток для четвертого семестра
     */
    private int fourthRecommendedCredits;

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
            List<Group> groups,
            int firstRecommendedCredits,
            int secondRecommendedCredits,
            int thirdRecommendedCredits,
            int fourthRecommendedCredits) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.groups = groups;
        this.firstRecommendedCredits = firstRecommendedCredits;
        this.secondRecommendedCredits = secondRecommendedCredits;
        this.thirdRecommendedCredits = thirdRecommendedCredits;
        this.fourthRecommendedCredits = fourthRecommendedCredits;
    }

    public Program(String name, String trainingDirection) {
        this.name = name;
        this.trainingDirection = trainingDirection;
    }

    public Program(UUID id, String name, String trainingDirection, List<Group> groups) {
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

    public List<Group> getGroups() {
        return groups;
    }

    public String getTrainingDirection() {
        return trainingDirection;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public int getFirstRecommendedCredits() {
        return firstRecommendedCredits;
    }

    public int getSecondRecommendedCredits() {
        return secondRecommendedCredits;
    }

    public int getThirdRecommendedCredits() {
        return thirdRecommendedCredits;
    }

    public int getFourthRecommendedCredits() {
        return fourthRecommendedCredits;
    }
}
