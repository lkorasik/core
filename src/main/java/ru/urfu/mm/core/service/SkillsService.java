package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.entity.Skill;
import ru.urfu.mm.core.entity.StudentSkills;
import ru.urfu.mm.core.repository.SkillRepository;
import ru.urfu.mm.core.repository.StudentSkillRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SkillsService {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private StudentSkillRepository studentSkillRepository;

    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    public List<StudentSkills> getSkillsForStudent(UUID studentId) {
        return studentSkillRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentId))
                .toList();
    }
}
