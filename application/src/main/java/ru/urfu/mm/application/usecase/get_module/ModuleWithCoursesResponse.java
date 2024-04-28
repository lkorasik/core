package ru.urfu.mm.application.usecase.get_module;

import java.util.List;
import java.util.UUID;

public record ModuleWithCoursesResponse(
        UUID id,
        String name,
        List<CourseResponse> courses
) {
}
