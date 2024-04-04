package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.SkillGateway;
import ru.urfu.mm.domainlegacy.StudentDesiredSkills;

import java.util.List;
import java.util.UUID;

public class GetDesiredSkillsForStudent {
    private final SkillGateway skillGateway;

    public GetDesiredSkillsForStudent(SkillGateway skillGateway) {
        this.skillGateway = skillGateway;
    }

    public List<StudentDesiredSkills> getDesiredSkillsForStudent(UUID studentId) {
        return skillGateway.getDesiredSkillsForStudent(studentId);
    }
}
