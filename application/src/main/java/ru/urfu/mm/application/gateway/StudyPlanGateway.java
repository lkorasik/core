package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.StudyPlan;

import java.util.List;

public interface StudyPlanGateway {
    void save(StudyPlan studyPlan, Program program);
    List<StudyPlan> findAllByProgram(Program program);
}
