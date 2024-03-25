package ru.urfu.mm.gateway;

import org.springframework.stereotype.Component;
import ru.urfu.mm.applicationlegacy.gateway.ModuleGateway;
import ru.urfu.mm.controller.modules.ModuleDTO;
import ru.urfu.mm.domainlegacy.Module;
import ru.urfu.mm.repository.EducationalModuleRepository;

import java.util.List;
import java.util.UUID;

@Component
public class ModuleGatewayImpl implements ModuleGateway {
    private final EducationalModuleRepository educationalModuleRepository;

    public ModuleGatewayImpl(EducationalModuleRepository educationalModuleRepository) {
        this.educationalModuleRepository = educationalModuleRepository;
    }

    @Override
    public Module find(UUID moduleId) {
        ru.urfu.mm.entity.Module entity = educationalModuleRepository.findById(moduleId).get();
        return new Module(
                entity.getId(),
                entity.getName()
        );
    }

    @Override
    public List<Module> getAllModules() {
        return educationalModuleRepository
                .findAll()
                .stream()
                .map(x -> new Module(x.getId(), x.getName()))
                .toList();
    }

    @Override
    public List<Module> getModulesByIds(List<UUID> modulesIds) {
        return educationalModuleRepository
                .findAll()
                .stream()
                .filter(x -> modulesIds.contains(x.getId()))
                .map(x -> new Module(x.getId(), x.getName()))
                .toList();
    }

    @Override
    public void save(Module module) {
        ru.urfu.mm.entity.Module entity = new ru.urfu.mm.entity.Module(module.getId(), module.getName());
        educationalModuleRepository.save(entity);
    }
}
