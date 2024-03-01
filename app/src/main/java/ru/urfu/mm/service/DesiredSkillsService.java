package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.dto.SkillDTO;
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

    public void saveSkillsForStudent(UUID studentId, List<SkillDTO> skills) {
        var currentSkills = desiredSkillsRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentId))
                .toList();
        desiredSkillsRepository.deleteAll(currentSkills);

        Student student = studentRepository.getReferenceById(studentId);

        var newSkills = skills
                .stream()
                .map(x -> Pair.of(skillRepository.findById(x.getId()).get(), x.getLevel()))
                .toList();
        desiredSkillsRepository
                .saveAll(
                        newSkills
                                .stream()
                                .map(x -> new StudentDesiredSkills(student, x.first(), x.second()))
                                .toList()
                );
    }
}
