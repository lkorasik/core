package ru.urfu.mm.application.usecase.create.student;

import java.util.UUID;

/**
 * Студент уже зарегистрирован
 */
public class StudentAlreadyRegisteredException extends RuntimeException {
    public StudentAlreadyRegisteredException(UUID studentId) {
        super("Student with id " + studentId + " is already registered");
    }
}
