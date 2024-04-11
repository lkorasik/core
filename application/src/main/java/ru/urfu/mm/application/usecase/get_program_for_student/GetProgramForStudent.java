package ru.urfu.mm.application.usecase.get_program_for_student;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.application.usecase.GetActualSemesters;
import ru.urfu.mm.domain.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.domain.SelectedCourses;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.domain.Student;

import java.util.*;

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

        var semesters = new ArrayList<>(semesterRepository
                .findAll()
                .stream()
                .filter(x -> program.containsKey(x.getId()))
                .toList());
        semesters.sort(Comparator.comparing(Semester::getSemesterNumber));
        var semesterIds = semesters
                .stream()
                .map(Semester::getId)
                .toList();

        var educationalProgramsWithSemestersAndCourses = courseGateway
                .getEducationalProgramToCoursesWithSemestersByEducationalProgram(student.getEducationalProgram().getId());

        var requiredCourses = educationalProgramsWithSemestersAndCourses
                .stream()
                .filter(EducationalProgramToCoursesWithSemesters::isRequiredCourse)
                .toList();
        var specialCourses = educationalProgramsWithSemestersAndCourses
                .stream()
                .filter(x -> !x.isRequiredCourse())
                .toList();
        var finalSemesters = semesterIds
                .stream()
                .map(x -> new FullSemesterDTO(
                        x,
                        requiredCourses
                                .stream()
                                .filter(y -> y.getSemester().getId().equals(x))
                                .map(y -> new ru.urfu.mm.controller.program.CourseDTO(
                                        y.getSemester().getId(),
                                        specialCourseRepository
                                                .findAllById(List.of(y.getSpecialCourse()
                                                        .getId()))
                                                .getFirst()
                                                .getName()
                                ))
                                .toList(),
                        specialCourses
                                .stream()
                                .filter(y -> y.getSemester().getId().equals(x))
                                .map(y -> new ru.urfu.mm.controller.program.CourseDTO(
                                        y.getSemester().getId(),
                                        specialCourseRepository
                                                .findAllById(List.of(y.getSpecialCourse().getId()))
                                                .getFirst()
                                                .getName()
                                ))
                                .toList(),
                        List.of()
                ))
                .toList();

        // todo: [semester -> [available course]]

        return new ProgramForStudentResponse(
                student.getEducationalProgram().getId(),
                student.getEducationalProgram().getName(),
                list,
                List.of()
        );
    }
}
