package ru.urfu.mm.application.usecase.get_study_plan;

import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.StudentSyllabus;

import java.util.UUID;

/**
 * Получить учебный план
 */
public class GetStudyPlan {
    private final ProgramGateway programGateway;

    public GetStudyPlan(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public StudentSyllabus getStudyPlan(UUID programId, int startYear) {
        throw new NotImplementedException();
//        return programGateway.getById(programId)
//                .getSyllabi()
//                .stream()
//                .filter(x -> x.getFirstSemesterPlan().getSemester().getYear() == startYear)
//                .findFirst()
//                .get();
    }
}
