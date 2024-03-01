package ru.urfu.mm.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "educational_program_to_courses_with_semesters")
public class EducationalProgramToCoursesWithSemesters {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "educational_program_id")
    private EducationalProgram educationalProgram;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;
    @ManyToOne
    @JoinColumn(name = "special_course_id")
    private SpecialCourse specialCourse;
    @Column
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
