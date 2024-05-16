package ru.urfu.mm.application.usecase.create_educational_program;

public record CreateProgramRequest(
        String name,
        String trainingDirection
) {
}
