package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.SkillGateway;
import ru.urfu.mm.applicationlegacy.gateway.StudentGateway;
import ru.urfu.mm.domainlegacy.SkillLevel;
import ru.urfu.mm.domainlegacy.Student;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SaveSkillsForStudent {
    private final SkillGateway skillGateway;
    private final StudentGateway studentGateway;

    public SaveSkillsForStudent(SkillGateway skillGateway, StudentGateway studentGateway) {
        this.skillGateway = skillGateway;
        this.studentGateway = studentGateway;
    }

    public void saveSkillsForStudent(UUID studentId, List<Map.Entry<UUID, SkillLevel>> skills) {
        skillGateway.deleteSkillsForStudent(studentId);
        Student student = studentGateway.getById(studentId);
        skillGateway.saveSkillsForStudent(student, skills);
    }
}
