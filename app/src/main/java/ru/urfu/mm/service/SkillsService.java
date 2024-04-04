package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.applicationlegacy.usecase.GetSkillsForStudent;
import ru.urfu.mm.entity.*;

import java.util.List;
import java.util.UUID;

@Service
public class SkillsService {
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
}
