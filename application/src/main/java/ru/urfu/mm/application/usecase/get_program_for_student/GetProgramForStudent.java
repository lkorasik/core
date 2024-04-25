package ru.urfu.mm.application.usecase.get_program_for_student;

import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.Student;

import java.util.List;
import java.util.UUID;

public class GetProgramForStudent {
    private final StudentGateway studentGateway;

    public GetProgramForStudent(StudentGateway studentGateway) {
        this.studentGateway = studentGateway;
    }

    public ProgramForStudentResponse getProgramForStudent(UUID studentId) {
        Student student = studentGateway.getById(studentId);

        return new ProgramForStudentResponse(
                student.getEducationalProgram().getId(),
                student.getEducationalProgram().getName(),
                List.of(),
                List.of()
        );
    }
}
