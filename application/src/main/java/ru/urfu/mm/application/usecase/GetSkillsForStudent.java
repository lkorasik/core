package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.SkillGateway;

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
