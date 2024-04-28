package ru.urfu.mm.application.usecase.update_program;

import java.util.UUID;

public record UpdateProgramRequest(
        UUID id,
        String name,
        String trainingDirection
) {
}
