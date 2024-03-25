package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.Semester;

import java.util.UUID;

public interface SemesterGateway {
    Semester getById(UUID semesterId);
}
