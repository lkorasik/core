package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Syllabus;

import java.util.List;

public interface StudyPlanGateway {
    void save(Syllabus syllabus, EducationalProgram educationalProgram);
    List<Syllabus> findAllByProgram(EducationalProgram educationalProgram);
}
