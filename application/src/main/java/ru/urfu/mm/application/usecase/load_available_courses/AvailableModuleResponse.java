package ru.urfu.mm.application.usecase.load_available_courses;

import java.util.List;
import java.util.UUID;

public record AvailableModuleResponse(
        UUID id,
        String name,
        List<AvailableCourseResponse> courses
) {}
