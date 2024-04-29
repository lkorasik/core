package ru.urfu.mm.application.usecase.generate_token;

import ru.urfu.mm.domain.AcademicGroup;

public class EducationalProgramNotExistsException extends RuntimeException {
    public EducationalProgramNotExistsException(AcademicGroup academicGroup) {
        super("No program found for group with id " + academicGroup.getId());
    }
}
