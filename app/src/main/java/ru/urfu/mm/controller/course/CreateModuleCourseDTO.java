package ru.urfu.mm.controller.course;

import ru.urfu.mm.persistance.entity.enums.Control;

import java.util.UUID;

record CreateModuleCourseDTO(
        String name,
        int credits,
        Control controlType,
        String description,
        UUID moduleId,
        String department,
        String teacher
) { }
