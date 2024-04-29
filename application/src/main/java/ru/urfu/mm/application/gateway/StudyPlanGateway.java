package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.StudyPlan;

import java.util.List;

public interface StudyPlanGateway {
    void save(StudyPlan studyPlan, EducationalProgram educationalProgram);
    List<StudyPlan> findAllByProgram(EducationalProgram educationalProgram);
}
