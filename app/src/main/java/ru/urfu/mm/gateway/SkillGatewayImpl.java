package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.applicationlegacy.gateway.SkillGateway;
import ru.urfu.mm.controller.skill.SkillInfoDTO;
import ru.urfu.mm.domainlegacy.Skill;
import ru.urfu.mm.repository.SkillRepository;

import java.util.List;

@Component
public class SkillGatewayImpl implements SkillGateway  {
    private final SkillRepository skillRepository;

    @Autowired
    public SkillGatewayImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
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
}
