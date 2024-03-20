package ru.urfu.mm.controller.course;

import java.util.List;

public record SelectedCoursesDTO(
        List<CoursesBySemesterDTO> coursesBySemesters
) { }
