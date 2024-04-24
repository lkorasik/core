package ru.urfu.mm.application.usecase.updateprogram;

import java.util.UUID;

public record UpdateProgramRequest(
        UUID id,
        String name,
        String trainingDirection
) {
}
