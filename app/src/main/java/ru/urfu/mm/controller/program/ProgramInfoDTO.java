package ru.urfu.mm.controller.program;

import java.util.UUID;

public record ProgramInfoDTO(
        UUID id,
        String name
) { }