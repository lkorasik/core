package ru.urfu.mm.controller.course;

import ru.urfu.mm.persistance.entity.enums.Control;

import java.util.UUID;

public record EditModuleCourseDTO(
        UUID courseId,
        String courseName,
        int creditsCount,
        Control control,
        String courseDescription,
        String department,
        String teacherName
) { }
