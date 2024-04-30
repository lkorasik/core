package ru.urfu.mm.gateway;

import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.EducationalModule;
import ru.urfu.mm.domain.enums.ControlTypes;
import ru.urfu.mm.persistance.entity.EducationalModuleEntity;
import ru.urfu.mm.persistance.repository.EducationalModuleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ModuleGatewayImpl implements ModuleGateway {
    private final EducationalModuleRepository educationalModuleRepository;

    public ModuleGatewayImpl(EducationalModuleRepository educationalModuleRepository) {
        this.educationalModuleRepository = educationalModuleRepository;
    }

    @Override
    public EducationalModule find(UUID moduleId) {
        EducationalModuleEntity entity = educationalModuleRepository.findById(moduleId).get();
        return new EducationalModule(
                entity.getId(),
                entity.getName()
        );
    }

    @Override
    public Optional<EducationalModule> getById(UUID moduleId) {
        return educationalModuleRepository.findById(moduleId)
                .map(x -> {
                    List<Course> courses = x.getCourses()
                            .stream()
                            .map(y -> new Course(
                                    y.getId(),
                                    y.getName(),
                                    y.getCreditsCount(),
                                    ControlTypes.values()[y.getControl().ordinal()],
                                    y.getDepartment(),
                                    y.getTeacherName())
                            )
                            .toList();
                    EducationalModule module = new EducationalModule(x.getId(), x.getName());
                    courses.forEach(module::addCourse);
                    return module;
                });
    }

    @Override
    public List<EducationalModule> getAllModules() {
        return educationalModuleRepository
                .findAll()
                .stream()
                .map(x -> new EducationalModule(x.getId(), x.getName()))
                .toList();
    }

    @Override
    public List<EducationalModule> getModulesByIds(List<UUID> modulesIds) {
        return educationalModuleRepository
                .findAll()
                .stream()
                .filter(x -> modulesIds.contains(x.getId()))
                .map(x -> new EducationalModule(x.getId(), x.getName()))
                .toList();
    }

    @Override
    public void save(EducationalModule educationalModule) {
        EducationalModuleEntity entity = new EducationalModuleEntity(educationalModule.getId(), educationalModule.getName());
        educationalModuleRepository.save(entity);
    }

    @Override
    public void delete(EducationalModule educationalModule) {
        educationalModuleRepository.deleteById(educationalModule.getId());
    }
}
