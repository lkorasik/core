package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.persistance.entity.EducationalProgramEntity;
import ru.urfu.mm.persistance.repository.ProgramRepository;
import ru.urfu.mm.persistance.repository.GroupRepository;
import ru.urfu.mm.persistance.repository.StudyPlanRepository;
import ru.urfu.mm.service.mapper.ProgramMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProgramGatewayImpl implements ProgramGateway {
    private final ProgramRepository programRepository;
    private final GroupRepository groupRepository;
    private final StudyPlanRepository studyPlanRepository;
    private final ProgramMapper programMapper;

    @Autowired
    public ProgramGatewayImpl(
            ProgramRepository programRepository,
            GroupRepository groupRepository,
            StudyPlanRepository studyPlanRepository,
            ProgramMapper programMapper) {
        this.programRepository = programRepository;
        this.groupRepository = groupRepository;
        this.studyPlanRepository = studyPlanRepository;
        this.programMapper = programMapper;
    }

    @Override
    public EducationalProgram getById(UUID id) {
        EducationalProgramEntity educationalProgramEntity = programRepository.getReferenceById(id);
        return programMapper.toDomain(educationalProgramEntity);
    }

    @Override
    public Optional<EducationalProgram> findById(UUID id) {
        return programRepository
                .findById(id)
                .map(x -> new EducationalProgram(
                        x.getId(),
                        x.getName(),
                        x.getTrainingDirection()
                ));
    }

    @Override
    public List<EducationalProgram> getAll() {
        return programRepository
                .findAll()
                .stream()
                .map(programMapper::toDomain)
                .toList();
    }

    @Override
    public void save(EducationalProgram educationalProgram) {
        EducationalProgramEntity entity = programMapper.toEntity(educationalProgram);
//        Iterable<GroupEntity> groups = groupRepository
//                .findAllById(educationalProgram.getGroups().stream().map(AcademicGroup::getId).toList());
//        groups.forEach(group -> group.setProgram(entity));
//        groupRepository.saveAll(groups);
        programRepository.save(entity);
    }

    public Optional<EducationalProgram> findByGroup(AcademicGroup academicGroup) {
        throw new NotImplementedException();
//        return groupRepository.findById(academicGroup.getId())
//                .map(GroupEntity::getProgram)
//                .map(x -> new EducationalProgram(x.getId(), x.getName(), x.getTrainingDirection()));
    }
}
