package ru.urfu.mm.application.usecase.create.student;

import ru.urfu.mm.domain.Group;

import java.util.UUID;

public class EducationalProgramNotExistsException extends RuntimeException {
    public EducationalProgramNotExistsException(UUID programId) {
        super("Program with id " + programId + " not exists.");
    }

    public EducationalProgramNotExistsException(Group group) {
        super("No program found for group with id " + group.getId());
    }
}
