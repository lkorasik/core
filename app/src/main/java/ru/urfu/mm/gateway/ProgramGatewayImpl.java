package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.persistance.entity.EducationalProgramEntity;
import ru.urfu.mm.persistance.repository.ProgramRepository;
import ru.urfu.mm.service.mapper.AcademicGroupMapper;
import ru.urfu.mm.service.mapper.ProgramMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProgramGatewayImpl implements ProgramGateway {
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private final AcademicGroupMapper academicGroupMapper;

    @Autowired
    public ProgramGatewayImpl(
            ProgramRepository programRepository,
            ProgramMapper programMapper,
            AcademicGroupMapper academicGroupMapper
    ) {
        this.programRepository = programRepository;
        this.programMapper = programMapper;
        this.academicGroupMapper = academicGroupMapper;
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
                        x.getTrainingDirection(),
                        x.getGroups()
                                .stream()
                                .map(academicGroupMapper::toDomain)
                                .toList()
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
        programRepository.save(entity);
    }
}
