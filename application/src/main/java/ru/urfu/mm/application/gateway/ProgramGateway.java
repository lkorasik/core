package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Group;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProgramGateway {
    EducationalProgram getById(UUID id);
    Optional<EducationalProgram> findById(UUID id);
    List<EducationalProgram> getAll();
    void save(EducationalProgram educationalProgram);
    Optional<EducationalProgram> findByGroup(Group group);
}
