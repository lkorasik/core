package ru.urfu.mm.application.usecase.create_course;

import java.util.UUID;

/**
 * Курс с указанным идентификатором не найден.
 */
public class ModuleNotFoundException extends RuntimeException {
    public ModuleNotFoundException(UUID courseId) {
        super("Module with id " + courseId + " not found");
    }
}
