package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.EducationalModule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleGateway {
    EducationalModule find(UUID moduleId);
    Optional<EducationalModule> getById(UUID moduleId);
    List<EducationalModule> getAllModules();
    void save(EducationalModule educationalModule);
    void delete(EducationalModule educationalModule);
    EducationalModule getModuleByCourse(UUID courseId);
}
