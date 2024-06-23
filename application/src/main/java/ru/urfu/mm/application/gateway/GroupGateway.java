package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.Student;

import java.util.Optional;
import java.util.UUID;

public interface GroupGateway {
    void save(AcademicGroup academicGroup);
    Optional<AcademicGroup> findById(UUID groupId);
}
