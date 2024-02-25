package ru.urfu.mm.core.dto;

import java.util.List;

public class SaveSkillsDTO {
    private List<SkillDTO> skills;

    public SaveSkillsDTO() {

    }

    public SaveSkillsDTO(List<SkillDTO> skills) {
        this.skills = skills;
    }

    public List<SkillDTO> getSkills() {
        return skills;
    }
}
