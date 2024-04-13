package ru.urfu.mm.controller.course;

import java.util.UUID;

public record AvailableCourseDTO(
        UUID id,
        String name
) {
}
