package ru.urfu.mm.application.usecase;

import java.util.List;
import java.util.UUID;

public class SelectedCourses {
    private final UUID semesterId;
    private final List<String> requiredCourses;
    private final List<String> specialCourses;
    private final List<String> science;

    public SelectedCourses(UUID semesterId, List<String> requiredCourses, List<String> specialCourses, List<String> science) {
        this.semesterId = semesterId;
        this.requiredCourses = requiredCourses;
        this.specialCourses = specialCourses;
        this.science = science;
    }

    public UUID getSemesterId() {
        return semesterId;
    }

    public List<String> getRequiredCourses() {
        return requiredCourses;
    }

    public List<String> getSpecialCourses() {
        return specialCourses;
    }

    public List<String> getScience() {
        return science;
    }
}
