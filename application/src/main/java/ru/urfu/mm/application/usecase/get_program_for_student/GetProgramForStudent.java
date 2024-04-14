package ru.urfu.mm.application.usecase.get_program_for_student;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GetProgramForStudent {
    private final StudentGateway studentGateway;
    private final ProgramGateway programGateway;
    private final SemesterGateway semesterGateway;
    private final CourseGateway courseGateway;

    public GetProgramForStudent(
            StudentGateway studentGateway,
            ProgramGateway programGateway,
            SemesterGateway semesterGateway,
            CourseGateway courseGateway) {
        this.studentGateway = studentGateway;
        this.programGateway = programGateway;
        this.semesterGateway = semesterGateway;
        this.courseGateway = courseGateway;
    }

    public ProgramForStudentResponse getProgramForStudent(UUID studentId) {
        Student student = studentGateway.getById(studentId);

        Map<UUID, Integer> semesterIdToCredits = programGateway
                .deserializeRecommendedCredits(student.getEducationalProgram());

        int[] recommendedCredits = new int[4];
        for (UUID semesterId : semesterIdToCredits.keySet()) {
            Semester semester = semesterGateway.getById(semesterId);
            recommendedCredits[semester.getSemesterNumber() - 1] = semesterIdToCredits.get(semesterId);
        }
        List<Integer> list = new ArrayList<>();
        for (int recommendedCredit : recommendedCredits) {
            list.add(recommendedCredit);
        }

        return new ProgramForStudentResponse(
                student.getEducationalProgram().getId(),
                student.getEducationalProgram().getName(),
                list,
                List.of()
        );
    }
}
