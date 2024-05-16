package ru.urfu.mm.application.usecase.get_program_for_student;

public record SelectedCourseResponse(
        String name,
        boolean isRequired
) {
}
