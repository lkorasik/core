package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.BaseSyllabus;

import java.util.List;
import java.util.UUID;

public interface BaseSyllabusPlanGateway {
    void save(BaseSyllabus syllabus);
    List<BaseSyllabus> getAllSyllabi(UUID programId);
}
