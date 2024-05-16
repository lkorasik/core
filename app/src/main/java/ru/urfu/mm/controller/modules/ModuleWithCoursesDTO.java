package ru.urfu.mm.controller.modules;

import java.util.List;
import java.util.UUID;

public record ModuleWithCoursesDTO(
        UUID id,
        String name,
        List<CourseDTO> courses
) {
}
