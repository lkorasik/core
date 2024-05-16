package ru.urfu.mm.application.usecase.create_course;

import ru.urfu.mm.domain.enums.ControlTypes;

import java.util.UUID;

public record CreateCourseRequest(
        String name,
        int credits,
        ControlTypes controlTypes,
        String description,
        UUID moduleId,
        String department,
        String teacher
) { }
