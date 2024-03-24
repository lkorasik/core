package ru.urfu.mm.domainlegacy;

import java.util.UUID;

public class EducationalProgramToCoursesWithSemesters {
    private UUID id;
    private EducationalProgram educationalProgram;
    private Semester semester;
    private SpecialCourse specialCourse;
    private boolean isRequiredCourse;

    public EducationalProgramToCoursesWithSemesters() {
    }

    public EducationalProgramToCoursesWithSemesters(UUID id, EducationalProgram educationalProgram, Semester semester, SpecialCourse specialCourse, boolean isRequiredCourse) {
        this.id = id;
        this.educationalProgram = educationalProgram;
        this.semester = semester;
        this.specialCourse = specialCourse;
        this.isRequiredCourse = isRequiredCourse;
    }

    public EducationalProgramToCoursesWithSemesters(EducationalProgram educationalProgram, Semester semester, SpecialCourse specialCourse, boolean isRequiredCourse) {
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

    public EducationalProgram getEducationalProgram() {
        return educationalProgram;
    }

    public void setEducationalProgram(EducationalProgram educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

}
