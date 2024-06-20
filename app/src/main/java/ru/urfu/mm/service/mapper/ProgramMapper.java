package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.persistance.entity.EducationalProgramEntity;

@Service
public class ProgramMapper {
    private final AcademicGroupMapper academicGroupMapper;

    @Autowired
    public ProgramMapper(AcademicGroupMapper academicGroupMapper) {
        this.academicGroupMapper = academicGroupMapper;
    }

    public EducationalProgramEntity toEntity(EducationalProgram program) {
        return new EducationalProgramEntity(
                program.getId(),
                program.getName(),
                program.getTrainingDirection(),
                program.getAcademicGroups()
                        .stream()
                        .map(academicGroupMapper::toEntity)
                        .toList()
        );
    }

    public EducationalProgram toDomain(EducationalProgramEntity program) {
        return new EducationalProgram(
                program.getId(),
                program.getName(),
                program.getTrainingDirection(),
                program.getGroups()
                        .stream()
                        .map(academicGroupMapper::toDomain)
                        .toList()
        );
    }
}
