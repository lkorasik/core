package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.Module;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.domain.SelectedCourses;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.domain.enums.SemesterType;
import ru.urfu.mm.domain.enums.ControlTypes;
import ru.urfu.mm.domain.enums.UserRole;
import ru.urfu.mm.persistance.entity.*;
import ru.urfu.mm.persistance.entity.StudentEntity;
import ru.urfu.mm.persistance.entity.enums.Control;
import ru.urfu.mm.persistance.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.persistance.repository.SelectedCoursesRepository;
import ru.urfu.mm.persistance.repository.SpecialCourseRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.List;
import java.util.UUID;

@Component
public class CourseGatewayImpl implements CourseGateway {
    private final SpecialCourseRepository courseRepository;
    private final SelectedCoursesRepository selectedCoursesRepository;
    private final EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    private final Mapper<ru.urfu.mm.persistance.entity.enums.SemesterType, SemesterType> semesterTypeToDomainMapper;
    private final Mapper<SemesterType, ru.urfu.mm.persistance.entity.enums.SemesterType> semesterTypeToEntityMapper;
    private final Mapper<Account, AccountEntity> userMapper;

    @Autowired
    public CourseGatewayImpl(
            SpecialCourseRepository courseRepository,
            SelectedCoursesRepository selectedCoursesRepository,
            EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository,
            Mapper<ru.urfu.mm.persistance.entity.enums.SemesterType, SemesterType> semesterTypeToDomainMapper,
            Mapper<SemesterType, ru.urfu.mm.persistance.entity.enums.SemesterType> semesterTypeToEntityMapper,
            Mapper<Account, AccountEntity> userMapper) {
        this.courseRepository = courseRepository;
        this.selectedCoursesRepository = selectedCoursesRepository;
        this.educationalProgramToCoursesWithSemestersRepository = educationalProgramToCoursesWithSemestersRepository;
        this.semesterTypeToDomainMapper = semesterTypeToDomainMapper;
        this.semesterTypeToEntityMapper = semesterTypeToEntityMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository
                .findAll()
                .stream()
                .map(x -> new Course(
                        x.getId(),
                        x.getName(),
                        x.getCreditsCount(),
                        ControlTypes.values()[x.getControl().ordinal()],
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
    public List<Course> getEducationalModuleCourses(UUID moduleId) {
        return courseRepository
                .findAll()
                .stream()
                .filter(x -> moduleId.equals(x.getEducationalModule().getId()))
                .map(x -> new Course(
                        x.getId(),
                        x.getName(),
                        x.getCreditsCount(),
                        ControlTypes.values()[x.getControl().ordinal()],
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
                                new Program(
                                        x.getStudent().getEducationalProgram().getId(),
                                        x.getStudent().getEducationalProgram().getName(),
                                        x.getStudent().getEducationalProgram().getTrainingDirection()
                                ),
                                new Group(
                                        x.getStudent().getGroup().getId(),
                                        x.getStudent().getGroup().getNumber()
                                ),
                                new Account(
                                        x.getStudent().getUser().getLogin(),
                                        x.getStudent().getUser().getPassword(),
                                        UserRole.values()[x.getStudent().getUser().getRole().ordinal()]
                                )
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                semesterTypeToDomainMapper.map(x.getSemester().getType())
                        ),
                        new Course(
                                x.getSpecialCourse().getId(),
                                x.getSpecialCourse().getName(),
                                x.getSpecialCourse().getCreditsCount(),
                                ControlTypes.values()[x.getSpecialCourse().getControl().ordinal()],
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
    public List<ProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemestersByEducationalProgram(UUID educationalProgramId) {
        return educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> x.getEducationalProgram().getId() == educationalProgramId)
                .map(x -> new ProgramToCoursesWithSemesters(
                        x.getId(),
                        new Program(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection()
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                semesterTypeToDomainMapper.map(x.getSemester().getType())
                        ),
                        new Course(
                                x.getSpecialCourse().getId(),
                                x.getSpecialCourse().getName(),
                                x.getSpecialCourse().getCreditsCount(),
                                ControlTypes.values()[x.getSpecialCourse().getControl().ordinal()],
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

    @Override
    public List<ProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemestersBySemesters(List<UUID> semestersIds) {
        return educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> semestersIds.contains(x.getSemester().getId()))
                .distinct()
                .toList()
                .stream()
                .map(x -> new ProgramToCoursesWithSemesters(
                        x.getId(),
                        new Program(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection()
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                semesterTypeToDomainMapper.map(x.getSemester().getType())
                        ),
                        new Course(
                                x.getSpecialCourse().getId(),
                                x.getSpecialCourse().getName(),
                                x.getSpecialCourse().getCreditsCount(),
                                ControlTypes.values()[x.getSpecialCourse().getControl().ordinal()],
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

    @Override
    public List<ProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemesterByModule(UUID moduleId) {
        return educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> x.getSpecialCourse().getEducationalModule().getId().equals(moduleId))
                .map(x -> new ProgramToCoursesWithSemesters(
                        x.getId(),
                        new Program(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection()
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                semesterTypeToDomainMapper.map(x.getSemester().getType())
                        ),
                        new Course(
                                x.getSpecialCourse().getId(),
                                x.getSpecialCourse().getName(),
                                x.getSpecialCourse().getCreditsCount(),
                                ControlTypes.values()[x.getSpecialCourse().getControl().ordinal()],
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

    @Override
    public List<UUID> getStudentBySelectedCourse(UUID courseId) {
        return selectedCoursesRepository
                .findAll()
                .stream()
                .filter(x -> x.getSpecialCourse().getId().equals(courseId))
                .map(x -> x.getStudent().getLogin())
                .distinct()
                .toList();
    }

    @Override
    public List<ProgramToCoursesWithSemesters> getRequiredCoursesForProgram(UUID programId) {
        return educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> x.getEducationalProgram().getId() == programId && x.isRequiredCourse())
                .map(x -> new ProgramToCoursesWithSemesters(
                        x.getId(),
                        new Program(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection()
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                semesterTypeToDomainMapper.map(x.getSemester().getType())
                        ),
                        new Course(
                                x.getSpecialCourse().getId(),
                                x.getSpecialCourse().getName(),
                                x.getSpecialCourse().getCreditsCount(),
                                ControlTypes.values()[x.getSpecialCourse().getControl().ordinal()],
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

    @Override
    public void deleteSelectedAllById(List<UUID> uuids) {
        selectedCoursesRepository.deleteAllById(uuids);
    }

    @Override
    public void saveSelectedCourses(List<SelectedCourses> courses) {
        selectedCoursesRepository
                .saveAll(
                        courses
                                .stream()
                                .map(x -> new ru.urfu.mm.persistance.entity.SelectedCourses(
                                        x.getId(),
                                        new StudentEntity(
                                                x.getStudent().getLogin(),
                                                new ProgramEntity(
                                                        x.getStudent().getProgram().getId(),
                                                        x.getStudent().getProgram().getName(),
                                                        x.getStudent().getProgram().getTrainingDirection()
                                                ),
                                                new GroupEntity(
                                                        x.getStudent().getGroup().getId(),
                                                        x.getStudent().getGroup().getNumber(),
                                                        ru.urfu.mm.persistance.entity.enums.Years.values()[x.getStudent().getGroup().getYear().ordinal()]
                                                ),
                                                userMapper.map(x.getStudent().getUser())
                                        ),
                                        new ru.urfu.mm.persistance.entity.Semester(
                                                x.getSemester().getId(),
                                                x.getSemester().getYear(),
                                                semesterTypeToEntityMapper.map(x.getSemester().getType())
                                        ),
                                        new SpecialCourse(
                                                x.getSpecialCourse().getId(),
                                                x.getSpecialCourse().getName(),
                                                x.getSpecialCourse().getCredits(),
                                                Control.values()[x.getSpecialCourse().getControl().ordinal()],
                                                x.getSpecialCourse().getDescription(),
                                                x.getSpecialCourse().getDepartment(),
                                                x.getSpecialCourse().getTeacher(),
                                                new ru.urfu.mm.persistance.entity.Module(
                                                        x.getSpecialCourse().getEducationalModule().getId(),
                                                        x.getSpecialCourse().getEducationalModule().getName()
                                                )
                                        )
                                ))
                                .toList());
    }

    @Override
    public Course getById(UUID id) {
        var x = courseRepository.getReferenceById(id);
        return new Course(
                x.getId(),
                x.getName(),
                x.getCreditsCount(),
                ControlTypes.values()[x.getControl().ordinal()],
                x.getDescription(),
                x.getDepartment(),
                x.getTeacherName(),
                new Module(
                        x.getEducationalModule().getId(),
                        x.getEducationalModule().getName()
                )
        );
    }

    @Override
    public void save(Course specialCourse) {
        courseRepository.save(
                new SpecialCourse(
                        specialCourse.getId(),
                        specialCourse.getName(),
                        specialCourse.getCredits(),
                        Control.values()[specialCourse.getControl().ordinal()],
                        specialCourse.getDescription(),
                        specialCourse.getDepartment(),
                        specialCourse.getTeacher(),
                        new ru.urfu.mm.persistance.entity.Module(
                                specialCourse.getEducationalModule().getId(),
                                specialCourse.getEducationalModule().getName()
                        )
                )
        );
    }

    @Override
    public void delete(UUID id) {
        courseRepository.deleteById(id);
    }
}
