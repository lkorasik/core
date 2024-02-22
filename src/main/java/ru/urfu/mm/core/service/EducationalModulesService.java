package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.entity.EducationalModule;
import ru.urfu.mm.core.repository.EducationalModuleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EducationalModulesService {
    @Autowired
    private EducationalModuleRepository educationalModuleRepository;

    public List<EducationalModule> getAllModules() {
        return educationalModuleRepository.findAll();
    }

    public List<EducationalModule> getModulesByIds(List<UUID> educationalModulesIds) {
        return educationalModuleRepository
                .findAll()
                .stream()
                .filter(x -> educationalModulesIds.contains(x.getId()))
                .map(x -> new EducationalModule(x.getId(), x.getName()))
                .toList();
    }
}
