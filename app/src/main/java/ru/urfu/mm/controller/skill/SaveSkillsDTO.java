package ru.urfu.mm.controller.skill;

import ru.urfu.mm.controller.recommendation.SkillDTO;

import java.util.List;

public record SaveSkillsDTO(
        List<SkillDTO> skills
) { }
