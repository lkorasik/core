package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.EducationalModule;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.domain.exception.NotImplementedException;
import ru.urfu.mm.persistance.entity.*;
import ru.urfu.mm.persistance.entity.enums.Control;
import ru.urfu.mm.persistance.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.persistance.repository.SelectedCoursesRepository;
import ru.urfu.mm.persistance.repository.SpecialCourseRepository;
import ru.urfu.mm.service.mapper.AccountMapper;
import ru.urfu.mm.service.mapper.CourseMapper;
import ru.urfu.mm.service.mapper.ModuleMapper;

import java.util.List;
import java.util.UUID;

@Component
public class CourseGatewayImpl implements CourseGateway {
    private final SpecialCourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final ModuleMapper moduleMapper;

    @Autowired
    public CourseGatewayImpl(
            SpecialCourseRepository courseRepository,
            CourseMapper courseMapper,
            ModuleMapper moduleMapper
    ) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.moduleMapper = moduleMapper;
    }

    @Override
    public List<Course> getAllCourses() {
        throw new NotImplementedException();
    }

    @Override
    public List<Course> getEducationalModuleCourses(UUID moduleId) {
        throw new NotImplementedException();
    }

    @Override
    public List<UUID> getStudentBySelectedCourse(UUID courseId) {
        throw new NotImplementedException();
    }

    @Override
    public Course getById(UUID id) {
        SpecialCourse entity = courseRepository.getReferenceById(id);
        return courseMapper.toDomain(entity);
    }

    @Override
    public void save(EducationalModule module, Course specialCourse) {
        SpecialCourse entity = courseMapper.toEntity(specialCourse);
        SpecialCourse entity2 = new SpecialCourse(
                entity.getId(),
                entity.getName(),
                entity.getCreditsCount(),
                entity.getControl(),
                entity.getDescription(),
                entity.getDepartment(),
                entity.getTeacherName(),
                moduleMapper.toEntity(module)
        );
        courseRepository.save(entity2);
    }

    @Override
    public void delete(UUID id) {
        throw new NotImplementedException();
    }
}
