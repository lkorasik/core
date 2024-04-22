package ru.urfu.mm.domain;

/**
 * Учебный план. В нем по семестрам расписано, какие модули и курсы доступны студентам, которые начали учиться в
 * определенном году.
 */
public class StudyPlan {
    private final SemesterPlan firstSemesterPlan;
    private final SemesterPlan secondSemesterPlan;
    private final SemesterPlan thirdSemesterPlan;
    private final SemesterPlan fourthSemesterPlan;

    public StudyPlan(
            SemesterPlan firstSemesterPlan,
            SemesterPlan secondSemesterPlan,
            SemesterPlan thirdSemesterPlan,
            SemesterPlan fourthSemesterPlan) {
        this.firstSemesterPlan = firstSemesterPlan;
        this.secondSemesterPlan = secondSemesterPlan;
        this.thirdSemesterPlan = thirdSemesterPlan;
        this.fourthSemesterPlan = fourthSemesterPlan;
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
