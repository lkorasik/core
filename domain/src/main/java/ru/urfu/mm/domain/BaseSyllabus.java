package ru.urfu.mm.domain;

import java.util.UUID;

/**
 * Базовый учебный план
 */
public class BaseSyllabus {
    /**
     * Идентификатор учебного плана
     */
    private final UUID id;
    /**
     * Первый семестр
     */
    private final BaseSemesterPlan firstSemesterPlan;
    /**
     * Второй семестр
     */
    private final BaseSemesterPlan secondSemesterPlan;
    /**
     * Третий семестр
     */
    private final BaseSemesterPlan thirdSemesterPlan;
    /**
     * Четвертый семестр
     */
    private final BaseSemesterPlan fourthSemesterPlan;

    public BaseSyllabus(
            UUID id,
            BaseSemesterPlan firstSemesterPlan,
            BaseSemesterPlan secondSemesterPlan,
            BaseSemesterPlan thirdSemesterPlan,
            BaseSemesterPlan fourthSemesterPlan
    ) {
        this.id = id;
        this.firstSemesterPlan = firstSemesterPlan;
        this.secondSemesterPlan = secondSemesterPlan;
        this.thirdSemesterPlan = thirdSemesterPlan;
        this.fourthSemesterPlan = fourthSemesterPlan;
    }
}
