package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Semester;

import java.util.List;
import java.util.UUID;

public interface SemesterGateway {
    void save(Semester semester);
    Semester getById(UUID semesterId);
    List<Semester> GetLaterOrEqual(int year);

    /**
     * Получить список семестров для всего преиода обуения, который начинается с определенного года.
     * @param startYear Год начала обучения.
     */
    List<Semester> getSemestersForEntireStudyPeriod(int startYear);
}
