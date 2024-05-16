package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.domain.EducationalModule;

import java.util.List;
import java.util.UUID;

public class GetModulesByIds {
    private final ModuleGateway moduleGateway;

    public GetModulesByIds(ModuleGateway moduleGateway) {
        this.moduleGateway = moduleGateway;
    }

    public List<EducationalModule> getModulesByIds(List<UUID> modulesIds) {
        return moduleGateway.getModulesByIds(modulesIds);
    }
}

