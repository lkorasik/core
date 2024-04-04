package ru.urfu.mm.controller.course;

import java.util.List;
import java.util.UUID;

public record CoursesBySemesterDTO(
        UUID semesterId,
        List<UUID> coursesIds
) { }
