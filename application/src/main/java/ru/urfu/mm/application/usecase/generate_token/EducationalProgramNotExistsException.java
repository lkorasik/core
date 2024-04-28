package ru.urfu.mm.application.usecase.generate_token;

import ru.urfu.mm.domain.Group;

public class EducationalProgramNotExistsException extends RuntimeException {
    public EducationalProgramNotExistsException(Group group) {
        super("No program found for group with id " + group.getId());
    }
}
