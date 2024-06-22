package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.EducationalModule;
import ru.urfu.mm.persistance.entity.SpecialCourse;
import ru.urfu.mm.persistance.entity.enums.Control;

@Service
public class CourseMapper {

    public Course toDomain(SpecialCourse course) {
        return new Course(
                course.getId(),
                course.getName(),
                course.getCreditsCount(),
                Control.toDomain(course.getControl()),
                course.getDescription(),
                course.getDepartment(),
                course.getTeacherName()
        );
    }

    public SpecialCourse toEntity(Course course) {
        return new SpecialCourse(
                course.getId(),
                course.getName(),
                course.getCredits(),
                Control.toEntity(course.getControl()),
                course.getDescription(),
                course.getDepartment(),
                course.getTeacher(),
                null
        );
    }
}
