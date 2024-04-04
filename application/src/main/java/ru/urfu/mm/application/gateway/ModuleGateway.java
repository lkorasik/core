package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Module;

import java.util.List;
import java.util.UUID;

public interface ModuleGateway {
    Module find(UUID moduleId);
    List<Module> getAllModules();
    List<Module> getModulesByIds(List<UUID> modulesIds);
    void save(Module module);
    void delete(Module module);
}
