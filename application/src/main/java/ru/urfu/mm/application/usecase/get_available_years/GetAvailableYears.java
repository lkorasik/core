package ru.urfu.mm.application.usecase.get_available_years;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Syllabus;

import java.util.List;
import java.util.UUID;

/**
 * Получить доступные года
 * 1. Надо достать все планы, котрые принадлежат группе.
 * 2. Достать из планов года, которые затем отразить в фронтенде
 */
public class GetAvailableYears {
    private final ProgramGateway programGateway;

    public GetAvailableYears(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public List<GetStudyPlanResponse> getStudyPlan(UUID programId) {
        EducationalProgram educationalProgram = programGateway.getById(programId);
        List<Syllabus> syllabi = educationalProgram.getSyllabi();

        return syllabi
                .stream()
                .map(x -> x.getFirstSemesterPlan().getSemester().getYear())
                .map(GetStudyPlanResponse::new)
                .toList();
    }
}
