package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.Module;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.entity.StudentEntity;
import ru.urfu.mm.entity.UserEntity;
import ru.urfu.mm.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.repository.SelectedCoursesRepository;
import ru.urfu.mm.repository.SpecialCourseRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.List;
import java.util.UUID;

@Component
public class CourseGatewayImpl implements CourseGateway {
    private final SpecialCourseRepository courseRepository;
    private final SelectedCoursesRepository selectedCoursesRepository;
    private final EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    private final Mapper<ru.urfu.mm.entity.SemesterType, ru.urfu.mm.domain.SemesterType> semesterTypeToDomainMapper;
    private final Mapper<ru.urfu.mm.domain.SemesterType, ru.urfu.mm.entity.SemesterType> semesterTypeToEntityMapper;
    private final Mapper<ru.urfu.mm.domain.User, UserEntity> userMapper;

    @Autowired
    public CourseGatewayImpl(
            SpecialCourseRepository courseRepository,
            SelectedCoursesRepository selectedCoursesRepository,
            EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository,
            Mapper<ru.urfu.mm.entity.SemesterType, SemesterType> semesterTypeToDomainMapper,
            Mapper<SemesterType, ru.urfu.mm.entity.SemesterType> semesterTypeToEntityMapper,
            Mapper<User, UserEntity> userMapper) {
        this.courseRepository = courseRepository;
        this.selectedCoursesRepository = selectedCoursesRepository;
        this.educationalProgramToCoursesWithSemestersRepository = educationalProgramToCoursesWithSemestersRepository;
        this.semesterTypeToDomainMapper = semesterTypeToDomainMapper;
        this.semesterTypeToEntityMapper = semesterTypeToEntityMapper;
        this.userMapper = userMapper;
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
    public List<SpecialCourse> getEducationalModuleCourses(UUID moduleId) {
        return courseRepository
                .findAll()
                .stream()
                .filter(x -> moduleId.equals(x.getEducationalModule().getId()))
                .map(x -> new SpecialCourse(
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
                                        x.getStudent().getEducationalProgram().getTrainingDirection(),
                                        x.getStudent().getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                                ),
                                x.getStudent().getGroup().getNumber(),
                                new User(
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
                        new SpecialCourse(
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
    public List<EducationalProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemestersByEducationalProgram(UUID educationalProgramId) {
        return educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> x.getEducationalProgram().getId() == educationalProgramId)
                .map(x -> new EducationalProgramToCoursesWithSemesters(
                        x.getId(),
                        new Program(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection(),
                                x.getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                semesterTypeToDomainMapper.map(x.getSemester().getType())
                        ),
                        new SpecialCourse(
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
    public List<EducationalProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemestersBySemesters(List<UUID> semestersIds) {
        return educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> semestersIds.contains(x.getSemester().getId()))
                .distinct()
                .toList()
                .stream()
                .map(x -> new EducationalProgramToCoursesWithSemesters(
                        x.getId(),
                        new Program(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection(),
                                x.getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                semesterTypeToDomainMapper.map(x.getSemester().getType())
                        ),
                        new SpecialCourse(
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
    public List<EducationalProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemesterByModule(UUID moduleId) {
        return educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> x.getSpecialCourse().getEducationalModule().getId().equals(moduleId))
                .map(x -> new EducationalProgramToCoursesWithSemesters(
                        x.getId(),
                        new Program(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection(),
                                x.getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                semesterTypeToDomainMapper.map(x.getSemester().getType())
                        ),
                        new SpecialCourse(
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
    public List<EducationalProgramToCoursesWithSemesters> getRequiredCoursesForProgram(UUID programId) {
        return educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> x.getEducationalProgram().getId() == programId && x.isRequiredCourse())
                .map(x -> new EducationalProgramToCoursesWithSemesters(
                        x.getId(),
                        new Program(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection(),
                                x.getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                        ),
                        new Semester(
                                x.getSemester().getId(),
                                x.getSemester().getYear(),
                                semesterTypeToDomainMapper.map(x.getSemester().getType())
                        ),
                        new SpecialCourse(
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
                                .map(x -> new ru.urfu.mm.entity.SelectedCourses(
                                        x.getId(),
                                        new StudentEntity(
                                                x.getStudent().getLogin(),
                                                new ru.urfu.mm.entity.EducationalProgram(
                                                        x.getStudent().getEducationalProgram().getId(),
                                                        x.getStudent().getEducationalProgram().getName(),
                                                        x.getStudent().getEducationalProgram().getTrainingDirection(),
                                                        x.getStudent().getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                                                ),
                                                userMapper.map(x.getStudent().getUser())
                                        ),
                                        new ru.urfu.mm.entity.Semester(
                                                x.getSemester().getId(),
                                                x.getSemester().getYear(),
                                                semesterTypeToEntityMapper.map(x.getSemester().getType())
                                        ),
                                        new ru.urfu.mm.entity.SpecialCourse(
                                                x.getSpecialCourse().getId(),
                                                x.getSpecialCourse().getName(),
                                                x.getSpecialCourse().getCreditsCount(),
                                                ru.urfu.mm.entity.Control.values()[x.getSpecialCourse().getControl().ordinal()],
                                                x.getSpecialCourse().getDescription(),
                                                x.getSpecialCourse().getDepartment(),
                                                x.getSpecialCourse().getTeacherName(),
                                                new ru.urfu.mm.entity.Module(
                                                        x.getSpecialCourse().getEducationalModule().getId(),
                                                        x.getSpecialCourse().getEducationalModule().getName()
                                                )
                                        )
                                ))
                                .toList());
    }

    @Override
    public SpecialCourse getById(UUID id) {
        var x = courseRepository.getReferenceById(id);
        return new SpecialCourse(
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
    public void save(SpecialCourse specialCourse) {
        courseRepository.save(
                new ru.urfu.mm.entity.SpecialCourse(
                        specialCourse.getId(),
                        specialCourse.getName(),
                        specialCourse.getCreditsCount(),
                        ru.urfu.mm.entity.Control.values()[specialCourse.getControl().ordinal()],
                        specialCourse.getDescription(),
                        specialCourse.getDepartment(),
                        specialCourse.getTeacherName(),
                        new ru.urfu.mm.entity.Module(
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
