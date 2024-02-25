package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.dto.SkillDTO;
import ru.urfu.mm.core.entity.Skill;
import ru.urfu.mm.core.entity.Student;
import ru.urfu.mm.core.entity.StudentSkills;
import ru.urfu.mm.core.repository.SkillRepository;
import ru.urfu.mm.core.repository.StudentRepository;
import ru.urfu.mm.core.repository.StudentSkillRepository;
import ru.urfu.mm.core.utils.Pair;

import java.util.List;
import java.util.UUID;

@Service
public class SkillsService {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private StudentSkillRepository studentSkillRepository;
    @Autowired
    private StudentRepository studentRepository;

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

    public void saveSkillsForStudent(UUID studentId, List<SkillDTO> skills) {
        var currentSkills = studentSkillRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentId))
                .toList();
        studentSkillRepository.deleteAll(currentSkills);

        Student student = studentRepository.getReferenceById(studentId);

        var newSkills = skills
                .stream()
                .map(x -> Pair.of(skillRepository.findById(x.getId()).get(), x.getLevel()))
                .toList();
        studentSkillRepository
                .saveAll(
                        newSkills
                                .stream()
                                .map(x -> new StudentSkills(student, x.first(), x.second()))
                                .toList()
                );
    }
}
