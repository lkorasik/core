package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.ModuleGateway;
import ru.urfu.mm.domainlegacy.Module;

import java.util.List;

public class GetAllModules {
    private final ModuleGateway moduleGateway;

    public GetAllModules(ModuleGateway moduleGateway) {
        this.moduleGateway = moduleGateway;
    }

    public List<Module> getAllModules() {
        return moduleGateway.getAllModules();
    }
}
