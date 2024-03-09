package ru.urfu.mm.exceptions;

import java.util.UUID;

public class EducationalProgramNotFoundException extends RuntimeException {
    public EducationalProgramNotFoundException(UUID id) {
        super("Educational program with id " + id + " not found");
    }
}
