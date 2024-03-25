package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.Module;

import java.util.List;
import java.util.UUID;

public interface ModuleGateway {
    Module find(UUID moduleId);
    List<Module> getAllModules();
}
