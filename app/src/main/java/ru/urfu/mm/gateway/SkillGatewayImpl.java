package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.applicationlegacy.gateway.SkillGateway;
import ru.urfu.mm.controller.skill.SkillInfoDTO;
import ru.urfu.mm.domainlegacy.*;
import ru.urfu.mm.repository.SkillRepository;
import ru.urfu.mm.repository.StudentSkillRepository;

import java.util.List;
import java.util.UUID;

@Component
public class SkillGatewayImpl implements SkillGateway  {
    private final SkillRepository skillRepository;
    private final StudentSkillRepository studentSkillRepository;

    @Autowired
    public SkillGatewayImpl(SkillRepository skillRepository, StudentSkillRepository studentSkillRepository) {
        this.skillRepository = skillRepository;
        this.studentSkillRepository = studentSkillRepository;
    }

    @Override
    public List<Skill> getAll() {
        return skillRepository
                .findAll()
                .stream()
                .map(x -> new Skill(
                        x.getId(),
                        x.getName()
                ))
                .toList();
    }

    @Override
    public List<StudentSkills> getSkillsForStudent(UUID studentId) {
        return studentSkillRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentId))
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
                                        UserRole.values()[x.getStudent().getUser().getRole().ordinal()]
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
}
