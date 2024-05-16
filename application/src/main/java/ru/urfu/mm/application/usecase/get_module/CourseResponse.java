package ru.urfu.mm.application.usecase.get_module;

import java.util.UUID;

public record CourseResponse(
        UUID id,
        String name
) {
}
