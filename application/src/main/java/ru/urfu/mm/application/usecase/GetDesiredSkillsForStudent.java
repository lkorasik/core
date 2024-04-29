package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.SkillGateway;

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
