package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.ProgramGateway;
import ru.urfu.mm.applicationlegacy.gateway.StudentGateway;
import ru.urfu.mm.domainlegacy.EducationalProgram;
import ru.urfu.mm.domainlegacy.Student;

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
        return programGateway.getById(student.getEducationalProgram().getId());
    }
}
