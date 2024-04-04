package ru.urfu.mm.controller.course;

import java.util.List;
import java.util.UUID;

public record GetCoursesDTO(
        UUID programId,
        List<UUID> semestersIds
) { }
