package ru.urfu.mm.application.usecase.get_modules_by_syllabus;

import ru.urfu.mm.application.gateway.BaseSyllabusPlanGateway;
import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.EducationalModule;

import java.util.UUID;

/**
 * Получить список модулей по базовому учебному плану
 */
public class GetModulesBySyllabus {
    private final BaseSyllabusPlanGateway baseSyllabusPlanGateway;
    private final ModuleGateway moduleGateway;

    public GetModulesBySyllabus(BaseSyllabusPlanGateway baseSyllabusPlanGateway, ModuleGateway moduleGateway) {
        this.baseSyllabusPlanGateway = baseSyllabusPlanGateway;
        this.moduleGateway = moduleGateway;
    }

    public EducationalModule getModulesBySyllabus(UUID programId, int startYear) {
        BaseSyllabus syllabus = baseSyllabusPlanGateway.getAllSyllabi(programId)
                .stream()
                .filter(x -> x.getFirstSemesterPlan().getSemester().getYear() == startYear)
                .findFirst()
                .get();

        UUID uuid = syllabus.getFirstSemesterPlan().getRequiredCourses().stream().map(x -> x.getId()).findFirst().get();

        return moduleGateway.getModuleByCourse(uuid);
    }
}
