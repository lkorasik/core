package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Семестровый учебный план
 */
public class SemesterPlan {
    private final UUID id;
    private final Semester semester;
    private final int recommendedCredits;
    private final List<Module> requiredModules;
    private final List<Module> specialModules;
    private final List<Module> scienceWork;

    public SemesterPlan(UUID id, Semester semester, int recommendedCredits) {
        this.id = id;
        this.semester = semester;
        this.recommendedCredits = recommendedCredits;
        requiredModules = new ArrayList<>();
        specialModules = new ArrayList<>();
        scienceWork = new ArrayList<>();
    }

    public SemesterPlan(
            UUID id,
            Semester semester,
            int recommendedCredits,
            List<Module> requiredModules,
            List<Module> specialModules,
            List<Module> scienceWork) {
        this.id = id;
        this.semester = semester;
        this.recommendedCredits = recommendedCredits;
        this.requiredModules = requiredModules;
        this.specialModules = specialModules;
        this.scienceWork = scienceWork;
    }

    public UUID getId() {
        return id;
    }

    public Semester getSemester() {
        return semester;
    }

    public int getRecommendedCredits() {
        return recommendedCredits;
    }

    public List<Module> getRequiredModules() {
        return requiredModules;
    }

    public List<Module> getSpecialModules() {
        return specialModules;
    }

    public List<Module> getScienceWork() {
        return scienceWork;
    }
}
