package ru.urfu.mm.application.usecase.generatetoken;

import ru.urfu.mm.domain.Group;

public class EducationalProgramNotExistsException extends RuntimeException {
    public EducationalProgramNotExistsException(Group group) {
        super("No program found for group with id " + group.getId());
    }
}
