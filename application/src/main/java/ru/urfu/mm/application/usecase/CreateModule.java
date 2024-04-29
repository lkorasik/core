package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.domain.EducationalModule;

import java.util.UUID;

/**
 * Создание модуля
 * 1. Создаем модуль
 * 2. Сохраняем модуль
 */
public class CreateModule {
    private final ModuleGateway moduleGateway;

    public CreateModule(ModuleGateway moduleGateway) {
        this.moduleGateway = moduleGateway;
    }

    public void createModule(String educationalModuleName) {
        EducationalModule educationalModule = new EducationalModule(UUID.randomUUID(), educationalModuleName);
        moduleGateway.save(educationalModule);
    }
}
