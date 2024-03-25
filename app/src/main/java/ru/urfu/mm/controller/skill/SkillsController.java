package ru.urfu.mm.controller.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.applicationlegacy.usecase.GetSkills;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.entity.SkillLevel;
import ru.urfu.mm.entity.StudentDesiredSkills;
import ru.urfu.mm.service.DesiredSkillsService;
import ru.urfu.mm.service.SkillsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
public class SkillsController extends AbstractAuthorizedController {
    @Autowired
    private SkillsService skillsService;
    @Autowired
    private DesiredSkillsService desiredSkillsService;
    @Autowired
    private GetSkills getSkills;

    @GetMapping
    public List<SkillInfoDTO> getSkills() {
        return getSkills
                .getSkills()
                .stream()
                .map(x -> new SkillInfoDTO(x.getId(), x.getName()))
                .toList();
    }

    @GetMapping("/actual")
    public List<SkillDTO> getActualSkills() {
        return skillsService
                .getSkillsForStudent(UUID.fromString(getUserToken()))
                .stream()
                .map(x -> new SkillDTO(x.getId(), x.getSkill().getName(), SkillLevel.values()[x.getLevel().ordinal()]))
                .toList();
    }

    @PostMapping("/actual")
    public void saveActualSkills(@RequestBody SaveSkillsDTO saveSkillsDTO) {
        skillsService.saveSkillsForStudent(UUID.fromString(getUserToken()), saveSkillsDTO.skills());
    }

    @GetMapping("/desired")
    public List<SkillInfoDTO> getDesiredSkills() {
        return desiredSkillsService.getSkillsForStudent(UUID.fromString(getUserToken()))
                .stream()
                .map(StudentDesiredSkills::getSkill)
                .toList()
                .stream()
                .map(x -> new SkillInfoDTO(x.getId(), x.getName()))
                .toList();
    }

    @PostMapping("/desired")
    public void saveDesiredSkills(@RequestBody SaveSkillsDTO saveSkillsDTO) {
        desiredSkillsService.saveSkillsForStudent(UUID.fromString(getUserToken()), saveSkillsDTO.skills());
    }
}
