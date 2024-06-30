package ru.urfu.mm.application.usecase.create_course;

import ru.urfu.mm.domain.exception.ApplicationException;

import java.util.UUID;

/**
 * Курс с указанным идентификатором не найден.
 */
public class ModuleNotFoundException extends ApplicationException {
    public ModuleNotFoundException(UUID courseId) {
        super("Module with id " + courseId + " not found");
    }
}
