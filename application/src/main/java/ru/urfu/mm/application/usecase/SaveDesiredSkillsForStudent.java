package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.SkillGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.enums.SkillLevel;
import ru.urfu.mm.domain.Student;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SaveDesiredSkillsForStudent {
    private final SkillGateway skillGateway;
    private final StudentGateway studentGateway;

    public SaveDesiredSkillsForStudent(SkillGateway skillGateway, StudentGateway studentGateway) {
        this.skillGateway = skillGateway;
        this.studentGateway = studentGateway;
    }

    public void saveSkillsForStudent(UUID studentId, List<Map.Entry<UUID, SkillLevel>> skills) {
        skillGateway.deleteDesiredSkillsForStudent(studentId);
        Student student = studentGateway.getById(studentId);
        skillGateway.saveDesiredSkillsForStudent(student, skills);
    }
}
