package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.controller.recommendation.SkillDTO;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.entity.StudentDesiredSkills;
import ru.urfu.mm.repository.DesiredSkillsRepository;
import ru.urfu.mm.repository.SkillRepository;
import ru.urfu.mm.repository.StudentRepository;
import ru.urfu.mm.utils.Pair;

import java.util.List;
import java.util.UUID;

@Service
public class DesiredSkillsService {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private DesiredSkillsRepository desiredSkillsRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<StudentDesiredSkills> getSkillsForStudent(UUID studentId) {
        var studentSkills = desiredSkillsRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentId))
                .toList();
        return studentSkills;
    }
}
