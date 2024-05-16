package ru.urfu.mm.application.usecase.get_program_for_student;

import java.util.List;
import java.util.UUID;

public record ProgramForStudentResponse(
        UUID programId,
        String name,
        List<Integer> recommendedCredits,
        List<SelectedCourseResponse> selectedCourses
) {
}
