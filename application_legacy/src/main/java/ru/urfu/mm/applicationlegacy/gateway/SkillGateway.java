package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SkillGateway {
    List<Skill> getAll();
    List<StudentSkills> getSkillsForStudent(UUID studentId);
    List<StudentDesiredSkills> getDesiredSkillsForStudent(UUID studentId);
    void deleteSkillsForStudent(UUID studentId);
    void deleteDesiredSkillsForStudent(UUID studentId);
    void saveSkillsForStudent(Student student, List<Map.Entry<UUID, SkillLevel>> skills);
    void saveDesiredSkillsForStudent(Student student, List<Map.Entry<UUID, SkillLevel>> skills);
}
