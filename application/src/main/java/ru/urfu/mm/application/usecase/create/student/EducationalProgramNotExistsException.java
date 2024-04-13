package ru.urfu.mm.application.usecase.create.student;

import java.util.UUID;

public class EducationalProgramNotExistsException extends RuntimeException {
    public EducationalProgramNotExistsException(UUID prorgamId) {
        super("Program with id " + prorgamId + " not exists.");
    }
}
