package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.SkillGateway;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.domain.enums.SkillLevel;
import ru.urfu.mm.domain.exception.NotImplementedException;
import ru.urfu.mm.persistance.entity.AccountEntity;
import ru.urfu.mm.persistance.repository.DesiredSkillsRepository;
import ru.urfu.mm.persistance.repository.SkillRepository;
import ru.urfu.mm.persistance.repository.StudentSkillRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class SkillGatewayImpl implements SkillGateway {
    private final SkillRepository skillRepository;
    private final StudentSkillRepository studentSkillRepository;
    private final DesiredSkillsRepository desiredSkillsRepository;
    private final Mapper<Account, AccountEntity> userMapper;

    @Autowired
    public SkillGatewayImpl(
            SkillRepository skillRepository,
            StudentSkillRepository studentSkillRepository,
            DesiredSkillsRepository desiredSkillsRepository,
            Mapper<Account, AccountEntity> userMapper) {
        this.skillRepository = skillRepository;
        this.studentSkillRepository = studentSkillRepository;
        this.desiredSkillsRepository = desiredSkillsRepository;
        this.userMapper = userMapper;
    }

//    @Override
//    public List<Skill> getAll() {
//        return skillRepository
//                .findAll()
//                .stream()
//                .map(x -> new Skill(
//                        x.getId(),
//                        x.getName()
//                ))
//                .toList();
//    }

//    @Override
//    public List<StudentSkills> getSkillsForStudent(UUID studentId) {
//        return studentSkillRepository
//                .findAll()
//                .stream()
//                .filter(x -> x.getStudent().getLogin().equals(studentId))
//                .map(x -> new StudentSkills(
//                        new Student(
//                                x.getStudent().getLogin(),
//                                new EducationalProgram(
//                                        x.getStudent().getEducationalProgram().getId(),
//                                        x.getStudent().getEducationalProgram().getName(),
//                                        x.getStudent().getEducationalProgram().getTrainingDirection()
//                                ),
//                                new AcademicGroup(
//                                        x.getStudent().getGroup().getId(),
//                                        x.getStudent().getGroup().getNumber()
//                                ),
//                                new Account(
//                                        x.getStudent().getUser().getLogin(),
//                                        x.getStudent().getUser().getPassword(),
//                                        UserRole.values()[x.getStudent().getUser().getRole().ordinal()]
//                                )
//                        ),
//                        new Skill(
//                                x.getSkill().getId(),
//                                x.getSkill().getName()
//                        ),
//                        SkillLevel.values()[x.getLevel().ordinal()]
//                ))
//                .toList();
//    }

//    @Override
//    public List<StudentDesiredSkills> getDesiredSkillsForStudent(UUID studentId) {
//        return desiredSkillsRepository
//                .findAll()
//                .stream()
//                .filter(x -> x.getStudent().getLogin().equals(studentId))
//                .map(x -> new StudentDesiredSkills(
//                        new Student(
//                                x.getStudent().getLogin(),
//                                new EducationalProgram(
//                                        x.getStudent().getEducationalProgram().getId(),
//                                        x.getStudent().getEducationalProgram().getName(),
//                                        x.getStudent().getEducationalProgram().getTrainingDirection()
//                                ),
//                                new AcademicGroup(
//                                        x.getStudent().getGroup().getId(),
//                                        x.getStudent().getGroup().getNumber()
//                                ),
//                                new Account(
//                                        x.getStudent().getUser().getLogin(),
//                                        x.getStudent().getUser().getPassword(),
//                                        UserRole.values()[x.getStudent().getUser().getRole().ordinal()]
//                                )
//                        ),
//                        new Skill(
//                                x.getSkill().getId(),
//                                x.getSkill().getName()
//                        ),
//                        SkillLevel.values()[x.getLevel().ordinal()]
//                ))
//                .toList();
//    }

    @Override
    public List<Skill> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public void deleteSkillsForStudent(UUID studentId) {
        var currentSkills = studentSkillRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentId))
                .toList();
        studentSkillRepository.deleteAll(currentSkills);
    }

    @Override
    public void deleteDesiredSkillsForStudent(UUID studentId) {
        var currentSkills = desiredSkillsRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentId))
                .toList();
        desiredSkillsRepository.deleteAll(currentSkills);
    }

    @Override
    public void saveSkillsForStudent(Student student, List<Map.Entry<UUID, SkillLevel>> skills) {
        throw new NotImplementedException();
    }

    @Override
    public void saveDesiredSkillsForStudent(Student student, List<Map.Entry<UUID, SkillLevel>> skills) {
        throw new NotImplementedException();
    }

//    @Override
//    public void saveSkillsForStudent(Student student, List<Map.Entry<UUID, SkillLevel>> skills) {
//        var newSkills = skills
//                .stream()
//                .map(x -> Map.entry(skillRepository.findById(x.getKey()).get(), x.getValue()))
//                .toList();
//        studentSkillRepository
//                .saveAll(
//                        newSkills
//                                .stream()
//                                .map(x -> new ru.urfu.mm.persistance.entity.StudentSkills(
//                                        new StudentEntity(
//                                                student.getId(),
//                                                new ProgramEntity(
//                                                        student.getProgram().getId(),
//                                                        student.getProgram().getName(),
//                                                        student.getProgram().getTrainingDirection()
//                                                ),
//                                                new GroupEntity(
//                                                        student.getGroup().getId(),
//                                                        student.getGroup().getNumber(),
//                                                        ru.urfu.mm.persistance.entity.enums.Years.values()[student.getGroup().getYear().ordinal()]
//                                                ),
//                                                userMapper.map(student.getAccount())
//                                        ),
//                                        x.getKey(),
//                                        ru.urfu.mm.persistance.entity.enums.SkillLevel.values()[x.getValue().ordinal()]
//                                ))
//                                .toList()
//                );
//    }

//    @Override
//    public void saveDesiredSkillsForStudent(Student student, List<Map.Entry<UUID, SkillLevel>> skills) {
//        var newSkills = skills
//                .stream()
//                .map(x -> Map.entry(skillRepository.findById(x.getKey()).get(), x.getValue()))
//                .toList();
//        desiredSkillsRepository
//                .saveAll(
//                        newSkills
//                                .stream()
//                                .map(x -> new ru.urfu.mm.persistance.entity.StudentDesiredSkills(
//                                        new StudentEntity(
//                                                student.getId(),
//                                                new ProgramEntity(
//                                                        student.getProgram().getId(),
//                                                        student.getProgram().getName(),
//                                                        student.getProgram().getTrainingDirection()
//                                                ),
//                                                new GroupEntity(
//                                                        student.getGroup().getId(),
//                                                        student.getGroup().getNumber(),
//                                                        ru.urfu.mm.persistance.entity.enums.Years.values()[student.getGroup().getYear().ordinal()]
//                                                ),
//                                                userMapper.map(student.getAccount())
//                                        ),
//                                        x.getKey(),
//                                        ru.urfu.mm.persistance.entity.enums.SkillLevel.values()[x.getValue().ordinal()]
//                                ))
//                                .toList()
//                );
//    }
}
