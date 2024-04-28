package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.persistance.entity.EducationalProgram;
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.repository.EducationalProgramRepository;
import ru.urfu.mm.persistance.repository.GroupRepository;
import ru.urfu.mm.persistance.repository.StudyPlanRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProgramGatewayImpl implements ProgramGateway {
    private final EducationalProgramRepository educationalProgramRepository;
    private final GroupRepository groupRepository;
    private final Mapper<EducationalProgram, Program> programMapper;
    private final StudyPlanRepository studyPlanRepository;

    @Autowired
    public ProgramGatewayImpl(
            EducationalProgramRepository educationalProgramRepository,
            GroupRepository groupRepository,
            Mapper<EducationalProgram, Program> programMapper,
            StudyPlanRepository studyPlanRepository) {
        this.educationalProgramRepository = educationalProgramRepository;
        this.groupRepository = groupRepository;
        this.programMapper = programMapper;
        this.studyPlanRepository = studyPlanRepository;
    }

    @Override
    public Program getById(UUID id) {
        EducationalProgram educationalProgram = educationalProgramRepository.getReferenceById(id);
        Program program = new Program(
                educationalProgram.getId(),
                educationalProgram.getName(),
                educationalProgram.getTrainingDirection()
        );
        List<Group> groups = groupRepository.findAllByEducationalProgram(educationalProgram)
                .stream()
                .map(x -> new Group(x.getId(), x.getNumber()))
                .toList();
        program.setGroups(groups);
        return program;
    }

    @Override
    public Optional<Program> findById(UUID id) {
        return educationalProgramRepository
                .findById(id)
                .map(x -> new Program(
                        x.getId(),
                        x.getName(),
                        x.getTrainingDirection()
                ));
    }

    @Override
    public List<Program> getAll() {
        return educationalProgramRepository
                .findAll()
                .stream()
                .map(programMapper::map)
                .toList();
    }

    @Override
    public void save(Program program) {
        EducationalProgram entity = new EducationalProgram(
                program.getId(),
                program.getName(),
                program.getTrainingDirection()
        );
        Iterable<GroupEntity> groups = groupRepository
                .findAllById(program.getGroups().stream().map(Group::getId).toList());
        groups.forEach(group -> group.setEducationalProgram(entity));
        groupRepository.saveAll(groups);
        educationalProgramRepository.save(entity);
    }

    @Override
    public Optional<Program> findByGroup(Group group) {
        return groupRepository.findById(group.getId())
                .map(GroupEntity::getEducationalProgram)
                .map(x -> new Program(
                        x.getId(),
                        x.getName(),
                        x.getTrainingDirection()
                ));
    }
}
