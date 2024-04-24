package ru.urfu.mm.application.usecase.getstudyplan;

import ru.urfu.mm.application.gateway.StudyPlanGateway;

/**
 * Получить учебный план
 * 1.
 */
public class GetStudyPlan {
    private final StudyPlanGateway studyPlanGateway;

    public GetStudyPlan(StudyPlanGateway studyPlanGateway) {
        this.studyPlanGateway = studyPlanGateway;
    }

    public void getStudyPlan() {
    }
}
