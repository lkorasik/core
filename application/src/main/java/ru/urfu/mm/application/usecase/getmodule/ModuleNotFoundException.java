package ru.urfu.mm.application.usecase.getmodule;

import java.util.UUID;

/**
 * Модуль не найден
 */
public class ModuleNotFoundException extends RuntimeException{
    public ModuleNotFoundException(UUID moduleId) {
        super("Module " + moduleId + " not found.");
    }
}
