package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.StudyPlan;

public interface StudyPlanGateway {
    void save(StudyPlan studyPlan, Program program);
}
