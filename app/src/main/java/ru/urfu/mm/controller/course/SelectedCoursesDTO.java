package ru.urfu.mm.controller.course;

import ru.urfu.mm.controller.course.CoursesBySemesterDTO;

import java.util.List;

public record SelectedCoursesDTO(
        List<CoursesBySemesterDTO> coursesBySemesters
) { }
