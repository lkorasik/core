package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.exception.IncorrectRecommendedCreditsCountException;
import ru.urfu.mm.application.exception.IncorrectSemestersCountException;
import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.Semester;

import java.util.List;
import java.util.Map;

public class CreateGroup {
    private final GroupGateway groupGateway;
    private final ProgramGateway programGateway;

    public CreateGroup(GroupGateway groupGateway, ProgramGateway programGateway) {
        this.groupGateway = groupGateway;
        this.programGateway = programGateway;
    }

    public void createGroup(Program program, String number, List<Semester> semesters, Map<Semester, Integer> recommendedCredits) {
        if (semesters.size() != 4) {
            throw new IncorrectSemestersCountException(semesters.size());
        }
        if (recommendedCredits.keySet().size() != 4) {
            throw new IncorrectSemestersCountException(recommendedCredits.keySet().size());
        }
        for(Semester semester : semesters) {
            if (recommendedCredits.get(semester) <= 0) {
                throw new IncorrectRecommendedCreditsCountException(recommendedCredits.get(semester));
            }
        }

        Group group = new Group(number, semesters, recommendedCredits);
        program.addGroup(group);

        groupGateway.saveGroup(group);
        programGateway.saveProgram(program);
    }
}
