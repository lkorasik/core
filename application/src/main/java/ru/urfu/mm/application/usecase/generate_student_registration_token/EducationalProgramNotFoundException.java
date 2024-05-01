package ru.urfu.mm.application.usecase.generate_student_registration_token;

import java.util.UUID;

/**
 * Программа не найдена
 */
public class EducationalProgramNotFoundException extends RuntimeException {
    public EducationalProgramNotFoundException(UUID groupId) {
        super("Program not found by group " + groupId);
    }
}
