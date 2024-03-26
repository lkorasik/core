package ru.urfu.mm.controller.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.applicationlegacy.usecase.GetDesiredSkillsForStudent;
import ru.urfu.mm.applicationlegacy.usecase.GetSkills;
import ru.urfu.mm.applicationlegacy.usecase.GetSkillsForStudent;
import ru.urfu.mm.applicationlegacy.usecase.SaveSkillsForStudent;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.entity.*;
import ru.urfu.mm.service.DesiredSkillsService;
import ru.urfu.mm.service.SkillsService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
public class SkillsController extends AbstractAuthorizedController {
    @Autowired
    private DesiredSkillsService desiredSkillsService;
    @Autowired
    private GetSkills getSkills;
    @Autowired
    private GetSkillsForStudent getSkillsForStudent;
    @Autowired
    private SaveSkillsForStudent saveSkillsForStudent;
    @Autowired
    private GetDesiredSkillsForStudent getDesiredSkillsForStudent;

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
        return getSkillsForStudent
                .getSkillsForStudent(UUID.fromString(getUserToken()))
                .stream()
                .map(x -> new SkillDTO(
                        x.getSkill().getId(),
                        x.getSkill().getName(),
                        SkillLevel.values()[x.getLevel().ordinal()]
                ))
                .toList();
    }

    @PostMapping("/actual")
    public void saveActualSkills(@RequestBody SaveSkillsDTO saveSkillsDTO) {
        List<Map.Entry<UUID, ru.urfu.mm.domainlegacy.SkillLevel>> skills = saveSkillsDTO.skills()
                .stream()
                .map(x -> Map.entry(x.id(), ru.urfu.mm.domainlegacy.SkillLevel.values()[x.level().ordinal()]))
                .toList();

        saveSkillsForStudent.saveSkillsForStudent(UUID.fromString(getUserToken()), skills);
    }

    @GetMapping("/desired")
    public List<SkillInfoDTO> getDesiredSkills() {
        return getDesiredSkillsForStudent
                .getDesiredSkillsForStudent(UUID.fromString(getUserToken()))
                .stream()
                .map(ru.urfu.mm.domainlegacy.StudentDesiredSkills::getSkill)
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
