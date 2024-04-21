package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Program;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ProgramGateway {
    Program getById(UUID id);
    Optional<Program> findById(UUID id);
    List<Program> getAll();
    void save(Program program);
    Optional<Program> findByGroup(Group group);
}
