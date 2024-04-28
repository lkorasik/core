package ru.urfu.mm.service;

import ru.urfu.mm.controller.course.CourseDTO;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.persistance.entity.Control;
import ru.urfu.mm.persistance.entity.SpecialCourse;

public class ModelConverterHelper {
    public static CourseDTO toDomain(SpecialCourse specialCourse) {
        return new CourseDTO(
                specialCourse.getId(),
                specialCourse.getName(),
                specialCourse.getCreditsCount(),
                specialCourse.getControl(),
                specialCourse.getDescription(),
                specialCourse.getEducationalModule().getId(),
                specialCourse.getTeacherName(),
                specialCourse.getDepartment()
        );
    }

    public static CourseDTO toDomain(Course specialCourse) {
        return new CourseDTO(
                specialCourse.getId(),
                specialCourse.getName(),
                specialCourse.getCreditsCount(),
                Control.values()[specialCourse.getControl().ordinal()],
                specialCourse.getDescription(),
                specialCourse.getEducationalModule().getId(),
                specialCourse.getTeacherName(),
                specialCourse.getDepartment()
        );
    }
}
