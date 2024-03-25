package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.Skill;

import java.util.List;

public interface SkillGateway {
    List<Skill> getAll();
}
