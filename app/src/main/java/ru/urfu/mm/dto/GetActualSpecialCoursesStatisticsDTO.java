package ru.urfu.mm.dto;

import java.util.List;
import java.util.UUID;

public class GetActualSpecialCoursesStatisticsDTO {
    private List<UUID> semestersId;

    public GetActualSpecialCoursesStatisticsDTO() {

    }

    public GetActualSpecialCoursesStatisticsDTO(List<UUID> semestersId) {
        this.semestersId = semestersId;
    }

    public List<UUID> getSemestersId() {
        return semestersId;
    }
}
