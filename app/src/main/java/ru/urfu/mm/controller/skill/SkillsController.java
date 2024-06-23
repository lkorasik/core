package ru.urfu.mm.controller.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.*;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.domain.exception.NotImplementedException;
import ru.urfu.mm.persistance.entity.enums.SkillLevel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(Endpoints.Skill.BASE)
public class SkillsController extends AbstractAuthorizedController implements SkillsControllerDescription {
    @Autowired
    private SaveSkillsForStudent saveSkillsForStudent;
    @Autowired
    private SaveDesiredSkillsForStudent saveDesiredSkillsForStudent;

    @Override
    public void saveActualSkills(@RequestBody SaveSkillsDTO saveSkillsDTO) {
        List<Map.Entry<UUID, ru.urfu.mm.domain.enums.SkillLevel>> skills = saveSkillsDTO.skills()
                .stream()
                .map(x -> Map.entry(x.id(), ru.urfu.mm.domain.enums.SkillLevel.values()[x.level().ordinal()]))
                .toList();

        saveSkillsForStudent.saveSkillsForStudent(UUID.fromString(getUserToken()), skills);
    }

    @Override
    public void saveDesiredSkills(@RequestBody SaveSkillsDTO saveSkillsDTO) {
        List<Map.Entry<UUID, ru.urfu.mm.domain.enums.SkillLevel>> skills = saveSkillsDTO.skills()
                .stream()
                .map(x -> Map.entry(x.id(), ru.urfu.mm.domain.enums.SkillLevel.values()[x.level().ordinal()]))
                .toList();

        saveDesiredSkillsForStudent.saveSkillsForStudent(UUID.fromString(getUserToken()), skills);
    }
}
