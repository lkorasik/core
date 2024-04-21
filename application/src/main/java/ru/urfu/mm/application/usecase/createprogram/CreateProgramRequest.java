package ru.urfu.mm.application.usecase.createprogram;

import java.util.List;

public record CreateProgramRequest(
        String name,
        List<Integer> recommendedCredits
) {
}
