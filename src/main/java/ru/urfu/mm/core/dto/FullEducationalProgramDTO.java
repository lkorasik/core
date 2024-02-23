package ru.urfu.mm.core.dto;

import java.util.List;
import java.util.UUID;

public class FullEducationalProgramDTO {
    private UUID id;
    private String title;
    private List<Integer> recomendedCredits;
    private List<FullSemesterDTO> semesters;

    public FullEducationalProgramDTO(UUID id, String title, List<Integer> recomendedCredits, List<FullSemesterDTO> semesters) {
        this.id = id;
        this.title = title;
        this.recomendedCredits = recomendedCredits;
        this.semesters = semesters;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getRecomendedCredits() {
        return recomendedCredits;
    }

    public List<FullSemesterDTO> getSemesters() {
        return semesters;
    }
}
