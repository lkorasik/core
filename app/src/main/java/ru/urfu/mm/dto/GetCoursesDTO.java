package ru.urfu.mm.dto;

import java.util.List;
import java.util.UUID;

public class GetCoursesDTO {
    private UUID educationalProgramId;
    private List<UUID> semestersIds;

    public GetCoursesDTO(UUID educationalProgramId, List<UUID> semestersIds) {
        this.educationalProgramId = educationalProgramId;
        this.semestersIds = semestersIds;
    }

    public UUID getEducationalProgramId() {
        return educationalProgramId;
    }

    public List<UUID> getSemestersIds() {
        return semestersIds;
    }
}
