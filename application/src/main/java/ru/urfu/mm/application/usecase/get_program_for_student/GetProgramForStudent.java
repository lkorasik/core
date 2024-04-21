package ru.urfu.mm.application.usecase.get_program_for_student;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GetProgramForStudent {
    private final StudentGateway studentGateway;

    public GetProgramForStudent(StudentGateway studentGateway) {
        this.studentGateway = studentGateway;
    }

    public ProgramForStudentResponse getProgramForStudent(UUID studentId) {
        Student student = studentGateway.getById(studentId);

        Program program = student.getEducationalProgram();
        List<Integer> recommendedCredits = List.of(
                program.getFirstRecommendedCredits(),
                program.getSecondRecommendedCredits(),
                program.getThirdRecommendedCredits(),
                program.getFourthRecommendedCredits()
        );

        return new ProgramForStudentResponse(
                student.getEducationalProgram().getId(),
                student.getEducationalProgram().getName(),
                recommendedCredits,
                List.of()
        );
    }
}
