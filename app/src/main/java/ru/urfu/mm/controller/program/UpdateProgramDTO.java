package ru.urfu.mm.controller.program;

import java.util.UUID;

public record UpdateProgramDTO(
        UUID id,
        String name,
        String trainingDirection
) { }
