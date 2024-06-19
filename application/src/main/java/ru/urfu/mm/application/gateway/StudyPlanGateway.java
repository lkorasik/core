package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.StudentSyllabus;

import java.util.List;

public interface StudyPlanGateway {
    void save(StudentSyllabus studentSyllabus, EducationalProgram educationalProgram);
    List<StudentSyllabus> findAllByProgram(EducationalProgram educationalProgram);
}
