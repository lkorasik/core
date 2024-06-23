package ru.urfu.mm.domain;

import java.util.UUID;

/**
 * Учебный план. В нем по семестрам расписано, какие модули и курсы доступны студентам, которые начали учиться в
 * определенном году.
 */
public class StudentSyllabus {
    /**
     * Идентификатор учебного плана
     */
    private final UUID id;
    /**
     * План на первый семестр
     */
    private final StudentSemesterPlan firstSemesterPlan;
    /**
     * План на второй семестр
     */
    private final StudentSemesterPlan secondSemesterPlan;
    /**
     * План на третий семестр
     */
    private final StudentSemesterPlan thirdSemesterPlan;
    /**
     * План на четвертый семестр
     */
    private final StudentSemesterPlan fourthSemesterPlan;

    public StudentSyllabus(
            UUID id,
            StudentSemesterPlan firstSemesterPlan,
            StudentSemesterPlan secondSemesterPlan,
            StudentSemesterPlan thirdSemesterPlan,
            StudentSemesterPlan fourthSemesterPlan
    ) {
        this.id = id;
        this.firstSemesterPlan = firstSemesterPlan;
        this.secondSemesterPlan = secondSemesterPlan;
        this.thirdSemesterPlan = thirdSemesterPlan;
        this.fourthSemesterPlan = fourthSemesterPlan;
    }
}
