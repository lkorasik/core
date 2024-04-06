package ru.urfu.mm.controller.course;

import java.util.UUID;

public record SelectedCourseNameDTO(String selectedCourseName, boolean isRequired, UUID courseId) { }
