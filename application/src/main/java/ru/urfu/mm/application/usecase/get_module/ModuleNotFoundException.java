package ru.urfu.mm.application.usecase.get_module;

import ru.urfu.mm.domain.exception.ApplicationException;

import java.util.UUID;

/**
 * Модуль не найден
 */
public class ModuleNotFoundException extends ApplicationException {
    public ModuleNotFoundException(UUID moduleId) {
        super("Module " + moduleId + " not found.");
    }
}
