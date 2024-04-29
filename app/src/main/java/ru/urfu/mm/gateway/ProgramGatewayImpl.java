package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.persistance.entity.ProgramEntity;
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
    private final Mapper<ProgramEntity, EducationalProgram> programMapper;
    private final StudyPlanRepository studyPlanRepository;

    @Autowired
    public ProgramGatewayImpl(
            ProgramRepository programRepository,
            GroupRepository groupRepository,
            Mapper<ProgramEntity, EducationalProgram> programMapper,
            StudyPlanRepository studyPlanRepository) {
        this.programRepository = programRepository;
        this.groupRepository = groupRepository;
        this.programMapper = programMapper;
        this.studyPlanRepository = studyPlanRepository;
    }

    @Override
    public EducationalProgram getById(UUID id) {
        ProgramEntity programEntity = programRepository.getReferenceById(id);
        EducationalProgram educationalProgram = new EducationalProgram(
                programEntity.getId(),
                programEntity.getName(),
                programEntity.getTrainingDirection()
        );
        List<Group> groups = groupRepository.findAllByProgram(programEntity)
                .stream()
                .map(x -> new Group(x.getId(), x.getNumber()))
                .toList();
        educationalProgram.setGroups(groups);
        return educationalProgram;
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
        ProgramEntity entity = new ProgramEntity(
                educationalProgram.getId(),
                educationalProgram.getName(),
                educationalProgram.getTrainingDirection()
        );
        Iterable<GroupEntity> groups = groupRepository
                .findAllById(educationalProgram.getGroups().stream().map(Group::getId).toList());
        groups.forEach(group -> group.setProgram(entity));
        groupRepository.saveAll(groups);
        programRepository.save(entity);
    }

    @Override
    public Optional<EducationalProgram> findByGroup(Group group) {
        throw new RuntimeException("Not implemented yet");
//        return groupRepository.findById(group.getId())
//                .map(GroupEntity::getEducationalProgram)
//                .map(x -> new Program(
//                        x.getId(),
//                        x.getName(),
//                        x.getTrainingDirection()
//                ));
    }
}
