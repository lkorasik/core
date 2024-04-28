package ru.urfu.mm.domain;

import java.util.UUID;

/**
 * Образовательная программа с курсами и семестрами.
 */
public class ProgramToCoursesWithSemesters {
    private UUID id;
    private Program program;
    private Semester semester;
    private Course specialCourse;
    private boolean isRequiredCourse;

    public ProgramToCoursesWithSemesters() {
    }

    public ProgramToCoursesWithSemesters(UUID id, Program program, Semester semester, Course specialCourse, boolean isRequiredCourse) {
        this.id = id;
        this.program = program;
        this.semester = semester;
        this.specialCourse = specialCourse;
        this.isRequiredCourse = isRequiredCourse;
    }

    public ProgramToCoursesWithSemesters(Program program, Semester semester, Course specialCourse, boolean isRequiredCourse) {
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

    public Course getSpecialCourse() {
        return specialCourse;
    }

    public void setSpecialCourse(Course specialCourse) {
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
