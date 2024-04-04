package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.domain.Module;

import java.util.List;
import java.util.UUID;

public class GetModulesByIds {
    private final ModuleGateway moduleGateway;

    public GetModulesByIds(ModuleGateway moduleGateway) {
        this.moduleGateway = moduleGateway;
    }

    public List<Module> getModulesByIds(List<UUID> modulesIds) {
        return moduleGateway.getModulesByIds(modulesIds);
    }
}

