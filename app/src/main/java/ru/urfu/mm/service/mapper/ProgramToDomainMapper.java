package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.entity.EducationalProgram;

@Component
public class ProgramToDomainMapper implements Mapper<EducationalProgram, Program> {
    @Override
    public Program map(EducationalProgram entity) {
        return new Program(
                entity.getId(),
                entity.getName(),
                entity.getTrainingDirection()
        );
    }
}
