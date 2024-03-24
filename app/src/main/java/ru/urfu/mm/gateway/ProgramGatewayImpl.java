package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.applicationlegacy.gateway.ProgramGateway;
import ru.urfu.mm.domainlegacy.EducationalProgram;
import ru.urfu.mm.repository.EducationalProgramRepository;

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
}
