package ru.urfu.mm.gateway;

import org.springframework.stereotype.Component;
import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.EducationalModule;
import ru.urfu.mm.domain.enums.ControlTypes;
import ru.urfu.mm.persistance.entity.EducationalModuleEntity;
import ru.urfu.mm.persistance.entity.enums.Control;
import ru.urfu.mm.persistance.repository.EducationalModuleRepository;
import ru.urfu.mm.service.mapper.ModuleMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ModuleGatewayImpl implements ModuleGateway {
    private final EducationalModuleRepository educationalModuleRepository;
    private final ModuleMapper moduleMapper;

    public ModuleGatewayImpl(EducationalModuleRepository educationalModuleRepository, ModuleMapper moduleMapper) {
        this.educationalModuleRepository = educationalModuleRepository;
        this.moduleMapper = moduleMapper;
    }

    @Override
    public EducationalModule find(UUID moduleId) {
        throw new NotImplementedException();
//        EducationalModuleEntity entity = educationalModuleRepository.findById(moduleId).get();
//        return new EducationalModule(
//                entity.getId(),
//                entity.getName()
//        );
    }

    @Override
    public Optional<EducationalModule> getById(UUID moduleId) {
        throw new NotImplementedException();
//        return educationalModuleRepository.findById(moduleId)
//                .map(x -> {
//                    List<Course> courses = x.getCourses()
//                            .stream()
//                            .map(y -> new Course(
//                                    y.getId(),
//                                    y.getName(),
//                                    y.getCreditsCount(),
//                                    ControlTypes.values()[y.getControl().ordinal()],
//                                    y.getDepartment(),
//                                    y.getTeacherName())
//                            )
//                            .toList();
//                    EducationalModule module = new EducationalModule(x.getId(), x.getName());
//                    courses.forEach(module::addCourse);
//                    return module;
//                });
    }

    @Override
    public List<EducationalModule> getAllModules() {
        return educationalModuleRepository
                .findAll()
                .stream()
                .map(moduleMapper::toDomain)
                .toList();
    }

    @Override
    public List<EducationalModule> getModulesByIds(List<UUID> modulesIds) {
        throw new NotImplementedException();
//        return educationalModuleRepository
//                .findAll()
//                .stream()
//                .filter(x -> modulesIds.contains(x.getId()))
//                .map(x -> new EducationalModule(x.getId(), x.getName()))
//                .toList();
    }

    @Override
    public void save(EducationalModule educationalModule) {
        throw new NotImplementedException();
//        EducationalModuleEntity entity = new EducationalModuleEntity(educationalModule.getId(), educationalModule.getName());
//        educationalModuleRepository.save(entity);
    }

    @Override
    public void delete(EducationalModule educationalModule) {
        throw new NotImplementedException();
//        educationalModuleRepository.deleteById(educationalModule.getId());
    }
}
