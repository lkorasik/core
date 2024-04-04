package ru.urfu.mm.controller.course;

import java.util.List;
import java.util.UUID;

public record GetSelectedCoursesDTO(
        List<UUID> semestersIds
) { }
