package ru.urfu.mm.application.usecase.getmodule;

import java.util.UUID;

public record CourseResponse(
        UUID id,
        String name
) {
}
