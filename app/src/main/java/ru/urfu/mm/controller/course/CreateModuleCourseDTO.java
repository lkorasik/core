package ru.urfu.mm.controller.course;

import ru.urfu.mm.persistance.entity.Control;

import java.util.UUID;

public record CreateModuleCourseDTO(
        String name,
        int credits,
        Control controlType,
        String description,
        UUID moduleId,
        String department,
        String teacher
) { }
