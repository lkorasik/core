package ru.urfu.mm.controller.program;

import ru.urfu.mm.application.usecase.get_program_for_student.ProgramForStudentResponse;

import java.util.List;
import java.util.UUID;

public record ProgramInfoDTO(
        UUID id,
        String name,
        List<Integer> recommendedCredits,
        List<SelectedCourseDTO> selectedCourses
) {
    public static ProgramInfoDTO from(ProgramForStudentResponse response) {
        return new ProgramInfoDTO(
                response.programId(),
                response.name(),
                response.recommendedCredits(),
                response.selectedCourses()
                        .stream()
                        .map(x -> new SelectedCourseDTO(x.name(), x.isRequired()))
                        .toList()
        );
    }
}