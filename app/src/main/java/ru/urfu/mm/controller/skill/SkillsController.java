package ru.urfu.mm.controller.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.*;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.domain.exception.NotImplementedException;
import ru.urfu.mm.persistance.entity.enums.SkillLevel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
public class SkillsController extends AbstractAuthorizedController {
    @Autowired
    private GetSkills getSkills;
//    @Autowired
//    private GetSkillsForStudent getSkillsForStudent;
    @Autowired
    private SaveSkillsForStudent saveSkillsForStudent;
//    @Autowired
//    private GetDesiredSkillsForStudent getDesiredSkillsForStudent;
    @Autowired
    private SaveDesiredSkillsForStudent saveDesiredSkillsForStudent;

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
        throw new NotImplementedException();
//        return getSkillsForStudent
//                .getSkillsForStudent(UUID.fromString(getUserToken()))
//                .stream()
//                .map(x -> new SkillDTO(
//                        x.getSkill().getId(),
//                        x.getSkill().getName(),
//                        SkillLevel.values()[x.getLevel().ordinal()]
//                ))
//                .toList();
    }

    @PostMapping("/actual")
    public void saveActualSkills(@RequestBody SaveSkillsDTO saveSkillsDTO) {
        List<Map.Entry<UUID, ru.urfu.mm.domain.enums.SkillLevel>> skills = saveSkillsDTO.skills()
                .stream()
                .map(x -> Map.entry(x.id(), ru.urfu.mm.domain.enums.SkillLevel.values()[x.level().ordinal()]))
                .toList();

        saveSkillsForStudent.saveSkillsForStudent(UUID.fromString(getUserToken()), skills);
    }

    @GetMapping("/desired")
    public List<SkillInfoDTO> getDesiredSkills() {
        throw new NotImplementedException();
//        return getDesiredSkillsForStudent
//                .getDesiredSkillsForStudent(UUID.fromString(getUserToken()))
//                .stream()
//                .map(ru.urfu.mm.domain.StudentDesiredSkills::getSkill)
//                .toList()
//                .stream()
//                .map(x -> new SkillInfoDTO(x.getId(), x.getName()))
//                .toList();
    }

    @PostMapping("/desired")
    public void saveDesiredSkills(@RequestBody SaveSkillsDTO saveSkillsDTO) {
        List<Map.Entry<UUID, ru.urfu.mm.domain.enums.SkillLevel>> skills = saveSkillsDTO.skills()
                .stream()
                .map(x -> Map.entry(x.id(), ru.urfu.mm.domain.enums.SkillLevel.values()[x.level().ordinal()]))
                .toList();

        saveDesiredSkillsForStudent.saveSkillsForStudent(UUID.fromString(getUserToken()), skills);
    }
}
