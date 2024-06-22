package ru.urfu.mm.application.usecase.get_base_syllabus;

import ru.urfu.mm.application.gateway.BaseSyllabusPlanGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.EducationalProgram;

import java.util.List;
import java.util.UUID;

/**
 * Получить базовый учебный план
 */
public class GetAllSyllabi {
    private final ProgramGateway programGateway;
    private final BaseSyllabusPlanGateway baseSyllabusPlanGateway;

    public GetAllSyllabi(ProgramGateway programGateway, BaseSyllabusPlanGateway baseSyllabusPlanGateway) {
        this.programGateway = programGateway;
        this.baseSyllabusPlanGateway = baseSyllabusPlanGateway;
    }

    public List<BaseSyllabus> getStudyPlan(UUID programId) {
        EducationalProgram program = programGateway.findById(programId).get();
        List<BaseSyllabus> baseSyllabi = program.getAcademicGroups()
                .stream()
                .map(AcademicGroup::getBaseSyllabus)
                .distinct()
                .toList();
        return baseSyllabi;
    }
}
