package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.SkillGateway;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.domain.enums.SkillLevel;
import ru.urfu.mm.domain.exception.NotImplementedException;
import ru.urfu.mm.persistance.repository.DesiredSkillsRepository;
import ru.urfu.mm.persistance.repository.SkillRepository;
import ru.urfu.mm.persistance.repository.StudentSkillRepository;
import ru.urfu.mm.service.mapper.AccountMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class SkillGatewayImpl implements SkillGateway {
    private final StudentSkillRepository studentSkillRepository;
    private final DesiredSkillsRepository desiredSkillsRepository;

    @Autowired
    public SkillGatewayImpl(
            StudentSkillRepository studentSkillRepository,
            DesiredSkillsRepository desiredSkillsRepository) {
        this.studentSkillRepository = studentSkillRepository;
        this.desiredSkillsRepository = desiredSkillsRepository;
    }

    @Override
    public List<Skill> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public void deleteSkillsForStudent(UUID studentId) {
        var currentSkills = studentSkillRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getId().equals(studentId))
                .toList();
        studentSkillRepository.deleteAll(currentSkills);
    }

    @Override
    public void deleteDesiredSkillsForStudent(UUID studentId) {
        var currentSkills = desiredSkillsRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getId().equals(studentId))
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
}
