package ru.urfu.mm.application.usecase.load_available_courses;

import java.util.UUID;

public record AvailableCourseResponse(
        UUID id,
        String name
) {
}
