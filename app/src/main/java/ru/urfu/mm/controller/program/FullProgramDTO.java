package ru.urfu.mm.controller.program;

import ru.urfu.mm.dto.FullSemesterDTO;

import java.util.List;
import java.util.UUID;

public record FullProgramDTO(
        UUID id,
        String title,
        List<Integer> recommendedCredits,
        List<FullSemesterDTO> semesters
) { }
