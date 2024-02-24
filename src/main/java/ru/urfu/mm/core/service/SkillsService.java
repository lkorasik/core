package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.entity.Skill;
import ru.urfu.mm.core.repository.SkillRepository;

import java.util.List;

@Service
public class SkillsService {
    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }
}
