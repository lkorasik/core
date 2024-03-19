package ru.urfu.mm.controller.recommendation;

import java.util.UUID;

public record SemesterDTO(
        UUID id,
        int year,
        int ordinalNumber
) { }
