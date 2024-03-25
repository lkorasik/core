package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.SkillGateway;
import ru.urfu.mm.domainlegacy.Skill;

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
