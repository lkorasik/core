package ru.urfu.mm.core.dto;

import java.util.List;
import java.util.UUID;

public class GetCoursesDTO {
    private UUID educationalProgramId;
    private List<UUID> semesterIds;

    public GetCoursesDTO(UUID educationalProgramId, List<UUID> semesterIds) {
        this.educationalProgramId = educationalProgramId;
        this.semesterIds = semesterIds;
    }

    public UUID getEducationalProgramId() {
        return educationalProgramId;
    }

    public List<UUID> getSemesterIds() {
        return semesterIds;
    }
}
