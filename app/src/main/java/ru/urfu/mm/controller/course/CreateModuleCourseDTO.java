package ru.urfu.mm.controller.course;

import ru.urfu.mm.entity.Control;

import java.util.UUID;

public record CreateModuleCourseDTO(
        String courseName,
        int creditsCount,
        Control controlType,
        String courseDescription,
        UUID moduleId,
        String department,
        String teacherName
) { }
