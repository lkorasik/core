package ru.urfu.mm.application.usecase.get_base_syllabus;

import java.util.List;
import java.util.UUID;

public record ModuleResponse(
        UUID id,
        String name,
        List<CourseResponse> courses
) {
}
