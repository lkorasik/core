package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.EducationalProgram;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProgramGateway {
    EducationalProgram getById(UUID id);
    List<EducationalProgram> getAll();
    Map<UUID, Integer> deserializeRecommendedCredits(EducationalProgram program);
}
