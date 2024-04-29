package ru.urfu.mm.application.usecase.get_study_plan;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Syllabus;

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

    public Syllabus getStudyPlan(UUID programId, int startYear) {
        EducationalProgram educationalProgram = programGateway.getById(programId);
        Syllabus syllabus = studyPlanGateway.findAllByProgram(educationalProgram)
                .stream()
                .filter(x -> x.getFirstSemesterPlan().getSemester().getYear() == startYear)
                .findFirst()
                .get();
        return syllabus;
    }
}
