package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.SkillGateway;
import ru.urfu.mm.applicationlegacy.gateway.StudentGateway;
import ru.urfu.mm.domainlegacy.StudentSkills;

import java.util.List;
import java.util.UUID;

public class GetSkillsForStudent {
    private final SkillGateway skillGateway;

    public GetSkillsForStudent(SkillGateway skillGateway) {
        this.skillGateway = skillGateway;
    }

    public List<StudentSkills> getSkillsForStudent(UUID studentId) {
        return skillGateway.getSkillsForStudent(studentId);

    }
}
