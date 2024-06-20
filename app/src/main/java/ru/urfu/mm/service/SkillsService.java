package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.persistance.entity.*;
import ru.urfu.mm.persistance.entity.enums.SkillLevel;
import ru.urfu.mm.persistance.entity.enums.Years;
import ru.urfu.mm.service.mapper.AccountMapper;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.List;
import java.util.UUID;

@Service
public class SkillsService {
//    @Autowired
//    private GetSkillsForStudent getSkillsForStudent;
    @Autowired
    private AccountMapper userMapper;

//    public List<StudentSkills> getSkillsForStudent(UUID studentId) {
//        return getSkillsForStudent
//                .getSkillsForStudent(studentId)
//                .stream()
//                .map(x -> new StudentSkills(
//                        new StudentEntity(
//                                x.getStudent().getId(),
//                                new ProgramEntity(
//                                        x.getStudent().getProgram().getId(),
//                                        x.getStudent().getProgram().getName(),
//                                        x.getStudent().getProgram().getTrainingDirection()
//                                ),
//                                new GroupEntity(
//                                        x.getStudent().getGroup().getId(),
//                                        x.getStudent().getGroup().getNumber(),
//                                        Years.values()[x.getStudent().getGroup().getYear().ordinal()]
//                                ),
//                                userMapper.map(x.getStudent().getAccount())
//                        ),
//                        new Skill(
//                                x.getSkill().getId(),
//                                x.getSkill().getName()
//                        ),
//                        SkillLevel.values()[x.getLevel().ordinal()]
//                ))
//                .toList();
//    }
}
