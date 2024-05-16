package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.SkillGateway;
import ru.urfu.mm.domain.Skill;

import java.util.List;

public class GetSkills {
    private final SkillGateway skillGateway;

    public GetSkills(SkillGateway skillGateway) {
        this.skillGateway = skillGateway;
    }

    public List<Skill> getSkills() {
        return skillGateway.getAll();
    }
}
