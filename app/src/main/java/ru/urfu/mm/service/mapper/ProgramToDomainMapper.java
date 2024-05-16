package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.persistance.entity.EducationalProgramEntity;

@Component
public class ProgramToDomainMapper implements Mapper<EducationalProgramEntity, EducationalProgram> {
    @Override
    public EducationalProgram map(EducationalProgramEntity entity) {
        return new EducationalProgram(
                entity.getId(),
                entity.getName(),
                entity.getTrainingDirection()
        );
    }
}
