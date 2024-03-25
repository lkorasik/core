package ru.urfu.mm.gateway;

import org.springframework.stereotype.Component;
import ru.urfu.mm.applicationlegacy.gateway.ModuleGateway;
import ru.urfu.mm.domainlegacy.Module;
import ru.urfu.mm.repository.EducationalModuleRepository;

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
}
