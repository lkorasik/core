package ru.urfu.mm.controller.skill;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.domain.exception.NotImplementedException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(name = "Skill", description = "Управление навыками")
@RequestMapping(Endpoints.Skill.BASE)
public interface SkillsControllerDescription {
    @PostMapping(Endpoints.Skill.ACTUAL)
    void saveActualSkills(@RequestBody SaveSkillsDTO saveSkillsDTO);

    @PostMapping(Endpoints.Skill.DESIRED)
    void saveDesiredSkills(@RequestBody SaveSkillsDTO saveSkillsDTO);
}
