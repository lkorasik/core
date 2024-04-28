package ru.urfu.mm.application.usecase.create_program;

public record CreateProgramRequest(
        String name,
        String trainingDirection
) {
}
