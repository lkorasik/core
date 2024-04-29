package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.persistance.entity.EducationalProgramEntity;
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.repository.ProgramRepository;
import ru.urfu.mm.persistance.repository.GroupRepository;
import ru.urfu.mm.persistance.repository.StudyPlanRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProgramGatewayImpl implements ProgramGateway {
    private final ProgramRepository programRepository;
    private final GroupRepository groupRepository;
    private final Mapper<EducationalProgramEntity, EducationalProgram> programMapper;
    private final StudyPlanRepository studyPlanRepository;

    @Autowired
    public ProgramGatewayImpl(
            ProgramRepository programRepository,
            GroupRepository groupRepository,
            Mapper<EducationalProgramEntity, EducationalProgram> programMapper,
            StudyPlanRepository studyPlanRepository) {
        this.programRepository = programRepository;
        this.groupRepository = groupRepository;
        this.programMapper = programMapper;
        this.studyPlanRepository = studyPlanRepository;
    }

    @Override
    public EducationalProgram getById(UUID id) {
        EducationalProgramEntity educationalProgramEntity = programRepository.getReferenceById(id);
        EducationalProgram program = new EducationalProgram(
                educationalProgramEntity.getId(),
                educationalProgramEntity.getName(),
                educationalProgramEntity.getTrainingDirection()
        );
        List<AcademicGroup> groups = educationalProgramEntity.getGroups()
                .stream()
                .map(x -> new AcademicGroup(x.getId(), x.getNumber()))
                .toList();
        program.getGroups().addAll(groups);
        return program;
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
                .map(programMapper::map)
                .toList();
    }

    @Override
    public void save(EducationalProgram educationalProgram) {
        EducationalProgramEntity entity = new EducationalProgramEntity(
                educationalProgram.getId(),
                educationalProgram.getName(),
                educationalProgram.getTrainingDirection()
        );
        Iterable<GroupEntity> groups = groupRepository
                .findAllById(educationalProgram.getGroups().stream().map(AcademicGroup::getId).toList());
        groups.forEach(group -> group.setProgram(entity));
        groupRepository.saveAll(groups);
        programRepository.save(entity);
    }

    public Optional<EducationalProgram> findByGroup(AcademicGroup academicGroup) {
        return groupRepository.findById(academicGroup.getId())
                .map(GroupEntity::getProgram)
                .map(x -> new EducationalProgram(x.getId(), x.getName(), x.getTrainingDirection()));
    }
}
