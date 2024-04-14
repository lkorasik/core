package ru.urfu.mm.domain;

import java.util.UUID;

public class EducationalProgramToCoursesWithSemesters {
    private UUID id;
    private Program program;
    private Semester semester;
    private SpecialCourse specialCourse;
    private boolean isRequiredCourse;

    public EducationalProgramToCoursesWithSemesters() {
    }

    public EducationalProgramToCoursesWithSemesters(UUID id, Program program, Semester semester, SpecialCourse specialCourse, boolean isRequiredCourse) {
        this.id = id;
        this.program = program;
        this.semester = semester;
        this.specialCourse = specialCourse;
        this.isRequiredCourse = isRequiredCourse;
    }

    public EducationalProgramToCoursesWithSemesters(Program program, Semester semester, SpecialCourse specialCourse, boolean isRequiredCourse) {
        this.program = program;
        this.semester = semester;
        this.specialCourse = specialCourse;
        this.isRequiredCourse = isRequiredCourse;
    }

    public UUID getId() {
        return id;
    }

    public boolean isRequiredCourse() {
        return isRequiredCourse;
    }

    public SpecialCourse getSpecialCourse() {
        return specialCourse;
    }

    public void setSpecialCourse(SpecialCourse specialCourse) {
        this.specialCourse = specialCourse;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Program getEducationalProgram() {
        return program;
    }

    public void setEducationalProgram(Program program) {
        this.program = program;
    }

}
