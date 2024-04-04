package ru.urfu.mm.controller.semester;

import java.util.UUID;

public record SemesterDTO(
        UUID id,
        int year,
        int ordinalNumber
) { }
