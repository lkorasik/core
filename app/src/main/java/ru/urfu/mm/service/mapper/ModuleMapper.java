package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.EducationalModule;
import ru.urfu.mm.persistance.entity.EducationalModuleEntity;
import ru.urfu.mm.persistance.entity.SpecialCourse;

import java.util.List;

@Service
public class ModuleMapper {
    private final CourseMapper courseMapper;

    @Autowired
    public ModuleMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public EducationalModule toDomain(EducationalModuleEntity entity) {
        List<Course> courses = entity.getCourses()
                .stream()
                .map(courseMapper::toDomain)
                .toList();
        return new EducationalModule(entity.getId(), entity.getName(), courses);
    }

    public EducationalModuleEntity toEntity(EducationalModule module) {
        List<SpecialCourse> courses = module.getCourses()
                .stream()
                .map(courseMapper::toEntity)
                .toList();
        return new EducationalModuleEntity(
                module.getId(),
                module.getName(),
                courses
        );
    }
}
