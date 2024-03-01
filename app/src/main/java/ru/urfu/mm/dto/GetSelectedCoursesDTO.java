package ru.urfu.mm.dto;

import java.util.List;
import java.util.UUID;

public class GetSelectedCoursesDTO {
    private List<UUID> semestersIds;

    public  GetSelectedCoursesDTO(){}

    public List<UUID> getSemestersIds() {
        return semestersIds;
    }

    public GetSelectedCoursesDTO(List<UUID> semestersIds) {
        this.semestersIds = semestersIds;
    }
}
