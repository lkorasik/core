package ru.urfu.mm.controller.program;

import java.util.UUID;

public record FullProgramDTO(
        UUID id,
        String title,
        String trainingDirection
) { }
