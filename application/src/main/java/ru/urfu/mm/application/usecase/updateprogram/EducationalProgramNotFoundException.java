package ru.urfu.mm.application.usecase.updateprogram;

import java.util.UUID;

/**
 * Образовательная программа не найдена.
 */
public class EducationalProgramNotFoundException extends RuntimeException {
    public EducationalProgramNotFoundException(UUID id) {
        super("Educational program with id " + id + " not found.");
    }
}
