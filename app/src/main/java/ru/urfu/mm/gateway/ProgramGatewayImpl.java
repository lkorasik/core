package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.repository.EducationalProgramRepository;

import java.util.List;
import java.util.UUID;

@Component
public class ProgramGatewayImpl implements ProgramGateway {
    private final EducationalProgramRepository educationalProgramRepository;

    @Autowired
    public ProgramGatewayImpl(EducationalProgramRepository educationalProgramRepository) {
        this.educationalProgramRepository = educationalProgramRepository;
    }

    @Override
    public EducationalProgram getById(UUID id) {
        ru.urfu.mm.entity.EducationalProgram educationalProgram = educationalProgramRepository.getReferenceById(id);
        return new EducationalProgram(
                educationalProgram.getId(),
                educationalProgram.getName(),
                educationalProgram.getTrainingDirection(),
                educationalProgram.getSemesterIdToRequiredCreditsCount()
        );
    }

    @Override
    public List<EducationalProgram> getAll() {
        return educationalProgramRepository
                .findAll()
                .stream()
                .map(x -> new EducationalProgram(
                        x.getId(),
                        x.getName(),
                        x.getTrainingDirection(),
                        x.getSemesterIdToRequiredCreditsCount()
                ))
                .toList();
    }
}
