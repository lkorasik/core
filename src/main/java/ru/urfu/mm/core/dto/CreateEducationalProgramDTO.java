package ru.urfu.mm.core.dto;

import java.util.List;

public class CreateEducationalProgramDTO {
    public String title;
    public List<Integer> recomendedCredits;
    public List<CreateSemesterDTO> semesters;

    public CreateEducationalProgramDTO(String title, List<Integer> recomendedCredits, List<CreateSemesterDTO> semesters) {
        this.title = title;
        this.recomendedCredits = recomendedCredits;
        this.semesters = semesters;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getRecomendedCredits() {
        return recomendedCredits;
    }

    public List<CreateSemesterDTO> getSemesters() {
        return semesters;
    }
}
