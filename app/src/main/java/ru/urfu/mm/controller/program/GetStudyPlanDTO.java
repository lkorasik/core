package ru.urfu.mm.controller.program;

import java.util.UUID;

public record GetStudyPlanDTO(
        UUID programId,
        int startYear
) {
}
