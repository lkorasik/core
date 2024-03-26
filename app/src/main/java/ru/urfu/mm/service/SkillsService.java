package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.applicationlegacy.usecase.GetSkillsForStudent;
import ru.urfu.mm.controller.recommendation.SkillDTO;
import ru.urfu.mm.domainlegacy.UserRole;
import ru.urfu.mm.entity.*;
import ru.urfu.mm.repository.SkillRepository;
import ru.urfu.mm.repository.StudentRepository;
import ru.urfu.mm.repository.StudentSkillRepository;
import ru.urfu.mm.utils.Pair;

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
    @Autowired
    private GetSkillsForStudent getSkillsForStudent;

    public List<StudentSkills> getSkillsForStudent(UUID studentId) {
        return getSkillsForStudent
                .getSkillsForStudent(studentId)
                .stream()
                .map(x -> new StudentSkills(
                        new Student(
                                x.getStudent().getLogin(),
                                new EducationalProgram(
                                        x.getStudent().getEducationalProgram().getId(),
                                        x.getStudent().getEducationalProgram().getName(),
                                        x.getStudent().getEducationalProgram().getTrainingDirection(),
                                        x.getStudent().getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                                ),
                                x.getStudent().getGroup(),
                                new User(
                                        x.getStudent().getUser().getLogin(),
                                        x.getStudent().getUser().getPassword(),
                                        ru.urfu.mm.entity.UserRole.values()[x.getStudent().getUser().getRole().ordinal()]
                                )
                        ),
                        new Skill(
                                x.getSkill().getId(),
                                x.getSkill().getName()
                        ),
                        SkillLevel.values()[x.getLevel().ordinal()]
                ))
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
                .map(x -> Pair.of(skillRepository.findById(x.id()).get(), x.level()))
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
