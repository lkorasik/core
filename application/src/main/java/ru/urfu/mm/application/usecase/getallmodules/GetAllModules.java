package ru.urfu.mm.application.usecase.getallmodules;

import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.domain.Module;

import java.util.List;

/**
 * Получить список всех модулей
 */
public class GetAllModules {
    private final ModuleGateway moduleGateway;

    public GetAllModules(ModuleGateway moduleGateway) {
        this.moduleGateway = moduleGateway;
    }

    public List<Module> getAllModules() {
        return moduleGateway.getAllModules();
    }
}
