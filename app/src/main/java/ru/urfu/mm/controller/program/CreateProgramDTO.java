package ru.urfu.mm.controller.program;

import java.util.List;

public record CreateProgramDTO(
        String title,
        List<Integer> recommendedCredits,
        List<CreateSemesterDTO> semesters
) { }
