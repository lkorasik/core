package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.persistance.entity.ProgramEntity;

@Component
public class ProgramToDomainMapper implements Mapper<ProgramEntity, EducationalProgram> {
    @Override
    public EducationalProgram map(ProgramEntity entity) {
        return new EducationalProgram(
                entity.getId(),
                entity.getName(),
                entity.getTrainingDirection()
        );
    }
}
