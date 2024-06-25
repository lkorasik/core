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
import ru.urfu.mm.service.mapper.CourseMapper;
import ru.urfu.mm.service.mapper.ModuleMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ModuleGatewayImpl implements ModuleGateway {
    private final EducationalModuleRepository educationalModuleRepository;
    private final ModuleMapper moduleMapper;
    private final CourseMapper courseMapper;

    public ModuleGatewayImpl(
            EducationalModuleRepository educationalModuleRepository,
            ModuleMapper moduleMapper,
            CourseMapper courseMapper
    ) {
        this.educationalModuleRepository = educationalModuleRepository;
        this.moduleMapper = moduleMapper;
        this.courseMapper = courseMapper;
    }

    @Override
    public EducationalModule find(UUID moduleId) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<EducationalModule> getById(UUID moduleId) {
        return educationalModuleRepository.findById(moduleId)
                .map(x -> {
                    List<Course> courses = x.getCourses()
                            .stream()
                            .map(courseMapper::toDomain)
                            .toList();
                    EducationalModule module = new EducationalModule(x.getId(), x.getName(), courses);
                    return module;
                });
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
    public void save(EducationalModule educationalModule) {
        EducationalModuleEntity entity = moduleMapper.toEntity(educationalModule);
        educationalModuleRepository.save(entity);
    }

    @Override
    public void delete(EducationalModule educationalModule) {
        throw new NotImplementedException();
    }
}
