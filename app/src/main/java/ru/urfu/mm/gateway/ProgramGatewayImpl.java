package ru.urfu.mm.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.GroupEntity;
import ru.urfu.mm.entity.Years;
import ru.urfu.mm.repository.EducationalProgramRepository;
import ru.urfu.mm.repository.GroupRepository;

import java.util.*;

@Component
public class ProgramGatewayImpl implements ProgramGateway {
    private final EducationalProgramRepository educationalProgramRepository;
    private final ObjectMapper mapper;
    private final GroupRepository groupRepository;

    @Autowired
    public ProgramGatewayImpl(
            EducationalProgramRepository educationalProgramRepository,
            ObjectMapper mapper,
            GroupRepository groupRepository) {
        this.educationalProgramRepository = educationalProgramRepository;
        this.mapper = mapper;
        this.groupRepository = groupRepository;
    }

    @Override
    public Program getById(UUID id) {
        ru.urfu.mm.entity.EducationalProgram educationalProgram = educationalProgramRepository.getReferenceById(id);
        return new Program(
                educationalProgram.getId(),
                educationalProgram.getName(),
                educationalProgram.getTrainingDirection(),
                educationalProgram.getSemesterIdToRequiredCreditsCount(),
                groupRepository.findAllByEducationalProgram(educationalProgram).stream().map(x -> new Group(x.getId(), x.getNumber())).toList()
        );
    }

    @Override
    public Optional<Program> findById(UUID id) {
        return educationalProgramRepository
                .findById(id)
                .map(x -> new Program(
                        x.getId(),
                        x.getName(),
                        x.getTrainingDirection(),
                        x.getSemesterIdToRequiredCreditsCount()
                ));
    }

    @Override
    public List<Program> getAll() {
        return educationalProgramRepository
                .findAll()
                .stream()
                .map(x -> new Program(
                        x.getId(),
                        x.getName(),
                        x.getTrainingDirection(),
                        x.getSemesterIdToRequiredCreditsCount()
                ))
                .toList();
    }

    @Override
    public void save(Program program) {
        EducationalProgram entity = new EducationalProgram(
                program.getId(),
                program.getName(),
                program.getTrainingDirection(),
                program.getSemesterIdToRequiredCreditsCount()
        );
        Iterable<GroupEntity> groups = groupRepository
                .findAllById(program.getGroups().stream().map(Group::getId).toList());
        groups.forEach(group -> group.setEducationalProgram(entity));
        groupRepository.saveAll(groups);
        educationalProgramRepository.save(entity);
    }

    @Override
    public Map<UUID, Integer> deserializeRecommendedCredits(Program program) {
        try {
            return mapper.readValue(program.getSemesterIdToRequiredCreditsCount(), new TypeReference<Map<UUID, Integer>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
            // todo: Set normal exception
        }
    }
}
