package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Program;
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
    private final Mapper<ProgramEntity, Program> programMapper;
    private final StudyPlanRepository studyPlanRepository;

    @Autowired
    public ProgramGatewayImpl(
            ProgramRepository programRepository,
            GroupRepository groupRepository,
            Mapper<ProgramEntity, Program> programMapper,
            StudyPlanRepository studyPlanRepository) {
        this.programRepository = programRepository;
        this.groupRepository = groupRepository;
        this.programMapper = programMapper;
        this.studyPlanRepository = studyPlanRepository;
    }

    @Override
    public Program getById(UUID id) {
        ProgramEntity programEntity = programRepository.getReferenceById(id);
        Program program = new Program(
                programEntity.getId(),
                programEntity.getName(),
                programEntity.getTrainingDirection()
        );
        List<Group> groups = groupRepository.findAllByProgram(programEntity)
                .stream()
                .map(x -> new Group(x.getId(), x.getNumber()))
                .toList();
        program.setGroups(groups);
        return program;
    }

    @Override
    public Optional<Program> findById(UUID id) {
        return programRepository
                .findById(id)
                .map(x -> new Program(
                        x.getId(),
                        x.getName(),
                        x.getTrainingDirection()
                ));
    }

    @Override
    public List<Program> getAll() {
        return programRepository
                .findAll()
                .stream()
                .map(programMapper::map)
                .toList();
    }

    @Override
    public void save(Program program) {
        ProgramEntity entity = new ProgramEntity(
                program.getId(),
                program.getName(),
                program.getTrainingDirection()
        );
        Iterable<GroupEntity> groups = groupRepository
                .findAllById(program.getGroups().stream().map(Group::getId).toList());
        groups.forEach(group -> group.setProgram(entity));
        groupRepository.saveAll(groups);
        programRepository.save(entity);
    }

    @Override
    public Optional<Program> findByGroup(Group group) {
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
