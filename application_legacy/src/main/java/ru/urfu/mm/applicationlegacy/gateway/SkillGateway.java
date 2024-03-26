package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.Skill;
import ru.urfu.mm.domainlegacy.SkillLevel;
import ru.urfu.mm.domainlegacy.Student;
import ru.urfu.mm.domainlegacy.StudentSkills;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SkillGateway {
    List<Skill> getAll();
    List<StudentSkills> getSkillsForStudent(UUID studentId);
    void deleteSkillsForStudent(UUID studentId);
    void saveSkillsForStudent(Student student, List<Map.Entry<UUID, SkillLevel>> skills);
}
