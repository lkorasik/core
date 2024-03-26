package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.Skill;
import ru.urfu.mm.domainlegacy.StudentSkills;

import java.util.List;
import java.util.UUID;

public interface SkillGateway {
    List<Skill> getAll();
    List<StudentSkills> getSkillsForStudent(UUID studentId);
}
