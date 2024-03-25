package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.EducationalProgram;

import java.util.List;
import java.util.UUID;

public interface ProgramGateway {
    EducationalProgram getById(UUID id);
    List<EducationalProgram> getAll();
}
