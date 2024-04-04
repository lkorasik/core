package ru.urfu.mm.controller.recommendation;

import java.util.List;
import java.util.UUID;

public record ModuleCoursesDTO(
        UUID moduleId,
        List<RecommendedCourseDTO> courses
) { }
