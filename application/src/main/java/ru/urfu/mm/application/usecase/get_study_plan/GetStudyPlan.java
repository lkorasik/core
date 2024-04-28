package ru.urfu.mm.application.usecase.get_study_plan;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.StudyPlan;

import java.util.UUID;

/**
 * Получить учебный план
 */
public class GetStudyPlan {
    private final StudyPlanGateway studyPlanGateway;
    private final ProgramGateway programGateway;

    public GetStudyPlan(StudyPlanGateway studyPlanGateway, ProgramGateway programGateway) {
        this.studyPlanGateway = studyPlanGateway;
        this.programGateway = programGateway;
    }

    public StudyPlan getStudyPlan(UUID programId, int startYear) {
        Program program = programGateway.getById(programId);
        StudyPlan studyPlan = studyPlanGateway.findAllByProgram(program)
                .stream()
                .filter(x -> x.getFirstSemesterPlan().getSemester().getYear() == startYear)
                .findFirst()
                .get();
        return studyPlan;
    }
}
