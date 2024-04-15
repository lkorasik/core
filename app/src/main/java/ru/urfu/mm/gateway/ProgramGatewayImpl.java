package ru.urfu.mm.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.repository.EducationalProgramRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProgramGatewayImpl implements ProgramGateway {
    private final EducationalProgramRepository educationalProgramRepository;
    private final ObjectMapper mapper;

    @Autowired
    public ProgramGatewayImpl(EducationalProgramRepository educationalProgramRepository, ObjectMapper mapper) {
        this.educationalProgramRepository = educationalProgramRepository;
        this.mapper = mapper;
    }

    @Override
    public Program getById(UUID id) {
        ru.urfu.mm.entity.EducationalProgram educationalProgram = educationalProgramRepository.getReferenceById(id);
        return new Program(
                educationalProgram.getId(),
                educationalProgram.getName(),
                educationalProgram.getTrainingDirection(),
                educationalProgram.getSemesterIdToRequiredCreditsCount()
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
