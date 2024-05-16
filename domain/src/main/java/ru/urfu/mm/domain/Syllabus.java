package ru.urfu.mm.domain;

import java.util.UUID;

/**
 * Учебный план. В нем по семестрам расписано, какие модули и курсы доступны студентам, которые начали учиться в
 * определенном году.
 */
public class Syllabus {
    /**
     * Идентификатор учебного плана
     */
    private final UUID id;
    /**
     * План на первый семестр
     */
    private final SemesterPlan firstSemesterPlan;
    /**
     * План на второй семестр
     */
    private final SemesterPlan secondSemesterPlan;
    /**
     * План на третий семестр
     */
    private final SemesterPlan thirdSemesterPlan;
    /**
     * План на четвертый семестр
     */
    private final SemesterPlan fourthSemesterPlan;

    public Syllabus(
            UUID id,
            SemesterPlan firstSemesterPlan,
            SemesterPlan secondSemesterPlan,
            SemesterPlan thirdSemesterPlan,
            SemesterPlan fourthSemesterPlan) {
        this.id = id;
        this.firstSemesterPlan = firstSemesterPlan;
        this.secondSemesterPlan = secondSemesterPlan;
        this.thirdSemesterPlan = thirdSemesterPlan;
        this.fourthSemesterPlan = fourthSemesterPlan;
    }

    public UUID getId() {
        return id;
    }

    public SemesterPlan getFirstSemesterPlan() {
        return firstSemesterPlan;
    }

    public SemesterPlan getSecondSemesterPlan() {
        return secondSemesterPlan;
    }

    public SemesterPlan getThirdSemesterPlan() {
        return thirdSemesterPlan;
    }

    public SemesterPlan getFourthSemesterPlan() {
        return fourthSemesterPlan;
    }
}
