package ru.urfu.mm.dto;

import java.util.Map;
import java.util.UUID;

public class EducationalProgramInfoDTO {
    private final UUID id;
    private final String name;
    private final Map<UUID, Integer> semesterIdToRequiredCreditsCount;

    public EducationalProgramInfoDTO(UUID id, String name, Map<UUID, Integer> semesterIdToRequiredCreditsCount) {
        this.id = id;
        this.name = name;
        this.semesterIdToRequiredCreditsCount = semesterIdToRequiredCreditsCount;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<UUID, Integer> getSemesterIdToRequiredCreditsCount() {
        return semesterIdToRequiredCreditsCount;
    }

    @Override
    public String toString() {
        return "EducationalProgramInfoDto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", semesterIdToRequiredCreditsCount=" + semesterIdToRequiredCreditsCount +
               '}';
    }
}
