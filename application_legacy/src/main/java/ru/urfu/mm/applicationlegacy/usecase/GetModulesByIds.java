package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.ModuleGateway;
import ru.urfu.mm.domainlegacy.Module;

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

