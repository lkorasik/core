package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.SkillGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.SkillLevel;
import ru.urfu.mm.domain.Student;

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
