package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Student;

import java.util.UUID;

public class GetEducationalProgram {
    private final ProgramGateway programGateway;
    private final StudentGateway studentGateway;

    public GetEducationalProgram(ProgramGateway programGateway, StudentGateway studentGateway) {
        this.programGateway = programGateway;
        this.studentGateway = studentGateway;
    }

    public EducationalProgram getEducationalProgram(UUID studentId) {
        Student student = studentGateway.getById(studentId);
        return programGateway.getById(student.getProgram().getId());
    }
}
