package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.BaseSyllabusPlanGateway;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.persistance.entity.BaseSyllabusEntity;
import ru.urfu.mm.persistance.entity.EducationalProgramEntity;
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.repository.BaseSemesterPlanRepository;
import ru.urfu.mm.persistance.repository.BaseSyllabusRepository;
import ru.urfu.mm.persistance.repository.ProgramRepository;
import ru.urfu.mm.service.mapper.BaseSyllabusMapper;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
public class BaseSyllabusGatewayImpl implements BaseSyllabusPlanGateway {
    private final BaseSyllabusRepository baseSyllabusRepository;
    private final BaseSyllabusMapper baseSyllabusMapper;
    private final ProgramRepository programRepository;
    private final BaseSemesterPlanRepository baseSemesterPlanRepository;

    @Autowired
    public BaseSyllabusGatewayImpl(
            BaseSyllabusRepository baseSyllabusRepository,
            BaseSyllabusMapper baseSyllabusMapper,
            ProgramRepository programRepository,
            BaseSemesterPlanRepository baseSemesterPlanRepository
    ) {
        this.baseSyllabusRepository = baseSyllabusRepository;
        this.baseSyllabusMapper = baseSyllabusMapper;
        this.programRepository = programRepository;
        this.baseSemesterPlanRepository = baseSemesterPlanRepository;
    }

    @Override
    public void save(BaseSyllabus syllabus) {
        BaseSyllabusEntity entity = baseSyllabusMapper.toEntity(syllabus);
        baseSemesterPlanRepository.save(entity.getFirstSemesterPlan());
        baseSemesterPlanRepository.save(entity.getSecondSemesterPlan());
        baseSemesterPlanRepository.save(entity.getThirdSemesterPlan());
        baseSemesterPlanRepository.save(entity.getFourthSemesterPlan());
        baseSyllabusRepository.save(entity);
    }

    @Override
    public List<BaseSyllabus> getAllSyllabi(UUID programId) {
        return programRepository.findById(programId)
                .stream()
                .map(EducationalProgramEntity::getGroups)
                .flatMap(Collection::stream)
                .map(GroupEntity::getBaseSyllabus)
                .map(baseSyllabusMapper::toDomain)
                .toList();
    }
}
