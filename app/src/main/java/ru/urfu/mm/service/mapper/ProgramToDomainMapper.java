package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.persistance.entity.ProgramEntity;

@Component
public class ProgramToDomainMapper implements Mapper<ProgramEntity, Program> {
    @Override
    public Program map(ProgramEntity entity) {
        return new Program(
                entity.getId(),
                entity.getName(),
                entity.getTrainingDirection()
        );
    }
}
