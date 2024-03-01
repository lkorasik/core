package ru.urfu.mm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.dto.SaveSkillsDTO;
import ru.urfu.mm.dto.SkillDTO;
import ru.urfu.mm.dto.SkillInfoDTO;
import ru.urfu.mm.entity.Skill;
import ru.urfu.mm.entity.StudentDesiredSkills;
import ru.urfu.mm.service.DesiredSkillsService;
import ru.urfu.mm.service.SkillsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
public class SkillsController {
    @Autowired
    private SkillsService skillsService;
    @Autowired
    private DesiredSkillsService desiredSkillsService;

    @GetMapping
    public List<SkillInfoDTO> getSkills() {
        return skillsService
                .getSkills()
                .stream()
                .map(x -> new SkillInfoDTO(x.getId(), x.getName()))
                .toList();
    }

    @GetMapping("/actual")
    public List<SkillDTO> getActualSkills() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        return skillsService
                .getSkillsForStudent(UUID.fromString(authentication.getName()))
                .stream()
                .map(x -> new SkillDTO(x.getId(), x.getSkill().getName(), x.getLevel()))
                .toList();
    }

    @PostMapping("/actual")
    public void saveActualSkills(@RequestBody SaveSkillsDTO saveSkillsDTO) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        skillsService.saveSkillsForStudent(UUID.fromString(authentication.getName()), saveSkillsDTO.getSkills());
    }

    @GetMapping("/desired")
    public List<Skill> getDesiredSkills() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        return desiredSkillsService.getSkillsForStudent(UUID.fromString(authentication.getName()))
                .stream()
                .map(StudentDesiredSkills::getSkill)
                .toList();
    }

    @PostMapping("/desired")
    public void saveDesiredSkills(@RequestBody SaveSkillsDTO saveSkillsDTO) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        desiredSkillsService.saveSkillsForStudent(UUID.fromString(authentication.getName()), saveSkillsDTO.getSkills());
    }
}
