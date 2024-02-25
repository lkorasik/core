package ru.urfu.mm.core.service;

import ru.urfu.mm.core.dto.SpecialCourseDTO;
import ru.urfu.mm.core.entity.SpecialCourse;

public class ModelConverterHelper {
    public static SpecialCourseDTO toDomain(SpecialCourse specialCourse) {
        return new SpecialCourseDTO(
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
}
