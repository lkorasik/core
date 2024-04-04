package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Semester;

import java.util.List;
import java.util.UUID;

public interface SemesterGateway {
    Semester getById(UUID semesterId);
    List<Semester> GetLaterOrEqual(int year);
}
