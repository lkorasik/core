package ru.urfu.mm.application.usecase.get_all_programs;

import java.util.UUID;

public record ProgramResponse(
        UUID id,
        String name
) {
}
