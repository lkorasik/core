package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.EducationalProgram;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProgramGateway {
    EducationalProgram getById(UUID id);
    Optional<EducationalProgram> findById(UUID id);
    List<EducationalProgram> getAll();
    void save(EducationalProgram educationalProgram);
    Optional<EducationalProgram> findByGroup(AcademicGroup group);
}
