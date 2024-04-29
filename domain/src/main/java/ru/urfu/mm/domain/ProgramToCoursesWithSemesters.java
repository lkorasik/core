package ru.urfu.mm.domain;

import java.util.UUID;

/**
 * Образовательная программа с курсами и семестрами.
 */
@Deprecated
public class ProgramToCoursesWithSemesters {
    private UUID id;
    private EducationalProgram educationalProgram;
    private Semester semester;
    private Course specialCourse;
    private boolean isRequiredCourse;

    public ProgramToCoursesWithSemesters() {
    }

    public ProgramToCoursesWithSemesters(UUID id, EducationalProgram educationalProgram, Semester semester, Course specialCourse, boolean isRequiredCourse) {
        this.id = id;
        this.educationalProgram = educationalProgram;
        this.semester = semester;
        this.specialCourse = specialCourse;
        this.isRequiredCourse = isRequiredCourse;
    }

    public ProgramToCoursesWithSemesters(EducationalProgram educationalProgram, Semester semester, Course specialCourse, boolean isRequiredCourse) {
        this.educationalProgram = educationalProgram;
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

    public EducationalProgram getEducationalProgram() {
        return educationalProgram;
    }

    public void setEducationalProgram(EducationalProgram educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

}
