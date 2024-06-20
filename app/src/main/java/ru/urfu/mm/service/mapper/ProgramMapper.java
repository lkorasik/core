package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.persistance.entity.EducationalProgramEntity;

@Service
public class ProgramMapper {
    public EducationalProgramEntity toEntity(EducationalProgram program) {
        return new EducationalProgramEntity(
                program.getId(),
                program.getName(),
                program.getTrainingDirection()
        );
    }
}
