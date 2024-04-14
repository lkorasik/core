package ru.urfu.mm.application.usecase.getmodule;

import java.util.List;
import java.util.UUID;

public record ModuleWithCoursesResponse(
        UUID id,
        String name,
        List<CourseResponse> courses
) {
}
