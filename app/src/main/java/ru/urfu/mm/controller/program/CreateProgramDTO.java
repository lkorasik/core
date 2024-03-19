package ru.urfu.mm.controller.program;

import ru.urfu.mm.dto.CreateSemesterDTO;

import java.util.List;

public record CreateProgramDTO(
        String title,
        List<Integer> recommendedCredits,
        List<CreateSemesterDTO> semesters
) { }
