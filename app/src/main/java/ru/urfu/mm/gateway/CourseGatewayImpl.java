package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.domainlegacy.*;
import ru.urfu.mm.domainlegacy.Module;
import ru.urfu.mm.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.repository.SelectedCoursesRepository;
import ru.urfu.mm.repository.SpecialCourseRepository;

import java.util.List;
import java.util.UUID;

@Component
public class CourseGatewayImpl implements CourseGateway {
    private final SpecialCourseRepository courseRepository;
    private final SelectedCoursesRepository selectedCoursesRepository;
    private final EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;

    @Autowired
    public CourseGatewayImpl(
            SpecialCourseRepository courseRepository,
            SelectedCoursesRepository selectedCoursesRepository,
            EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository) {
        this.courseRepository = courseRepository;
        this.selectedCoursesRepository = selectedCoursesRepository;
        this.educationalProgramToCoursesWithSemestersRepository = educationalProgramToCoursesWithSemestersRepository;
    }

    @Override
    public List<SpecialCourse> getAllCourses() {
        return courseRepository
                .findAll()
                .stream()
                .map(x -> new SpecialCourse(
                        x.getId(),
                        x.getName(),
                        x.getCreditsCount(),
                        Control.values()[x.getControl().ordinal()],
                        x.getDescription(),
                        x.getDepartment(),
                        x.getTeacherName(),
                        new Module(
                                x.getEducationalModule().getId(),
                                x.getEducationalModule().getName()
                        )
                ))
                .toList();
    }

    @Override
    public List<SpecialCourse> getEducationalModuleCourses(UUID moduleId) {
        return courseRepository
                .findAll()
                .stream()
                .filter(x -> moduleId.equals(x.getEducationalModule().getId()))
                .map(x -> new SpecialCourse(
                        x.getId(),
                        x.getName(),
                        x.getCreditsCount(),
                        Control.values()[x.getControl().ordinal()],
                        x.getDescription(),
                        x.getDepartment(),
                        x.getTeacherName(),
                        new Module(
                                x.getEducationalModule().getId(),
                                x.getEducationalModule().getName()
                        )
                ))
                .toList();
    }

    @Override
    public List<SelectedCourses> getSelectedCourses(UUID studentId) {
        return selectedCoursesRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentId))
                .map(x -> new SelectedCourses(
                        x.getId(),
                        new Student(
                                x.getStudent().getLogin(),
                                new EducationalProgram(
                                        x.getStudent().getEducationalProgram().getId(),
                                        x.getStudent().getEducationalProgram().getName(),
                                        x.getStudent().getEducationalProgram().getTrainingDirection(),
                                        x.getStudent().getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                                ),
                                x.getStudent().getGroup(),
                                new User(
                                        x.getStudent().getUser().getLogin(),
                                        x.getStudent().getUser().getPassword(),
                                        UserRole.values()[x.getStudent().getUser().getRole().ordinal()]
                                )
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                x.getSemester().getSemesterNumber()
                        ),
                        new SpecialCourse(
                                x.getSpecialCourse().getId(),
                                x.getSpecialCourse().getName(),
                                x.getSpecialCourse().getCreditsCount(),
                                Control.values()[x.getSpecialCourse().getControl().ordinal()],
                                x.getSpecialCourse().getDescription(),
                                x.getSpecialCourse().getDepartment(),
                                x.getSpecialCourse().getTeacherName(),
                                new Module(
                                        x.getSpecialCourse().getEducationalModule().getId(),
                                        x.getSpecialCourse().getEducationalModule().getName()
                                )
                        )
                ))
                .toList();
    }

    @Override
    public List<EducationalProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemestersByEducationalProgram(UUID educationalProgramId) {
        return educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> x.getEducationalProgram().getId() == educationalProgramId)
                .map(x -> new EducationalProgramToCoursesWithSemesters(
                        x.getId(),
                        new EducationalProgram(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection(),
                                x.getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                x.getSemester().getSemesterNumber()
                        ),
                        new SpecialCourse(
                                x.getSpecialCourse().getId(),
                                x.getSpecialCourse().getName(),
                                x.getSpecialCourse().getCreditsCount(),
                                Control.values()[x.getSpecialCourse().getControl().ordinal()],
                                x.getSpecialCourse().getDescription(),
                                x.getSpecialCourse().getDepartment(),
                                x.getSpecialCourse().getTeacherName(),
                                new Module(
                                        x.getSpecialCourse().getEducationalModule().getId(),
                                        x.getSpecialCourse().getEducationalModule().getName()
                                )
                        ),
                        x.isRequiredCourse()
                ))
                .toList();
    }
}
