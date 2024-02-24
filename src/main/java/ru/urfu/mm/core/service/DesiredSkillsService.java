package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.entity.Skill;
import ru.urfu.mm.core.entity.StudentDesiredSkills;
import ru.urfu.mm.core.repository.DesiredSkillsRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DesiredSkillsService {
    @Autowired
    private DesiredSkillsRepository desiredSkillsRepository;

    public List<Skill> getSkillsForStudent(UUID studentId) {
        var studentSkills = desiredSkillsRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentId))
                .toList();
        return studentSkills
                .stream()
                .map(StudentDesiredSkills::getSkill)
                .toList();
    }
}
