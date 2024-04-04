package ru.urfu.mm.domain;

import java.util.UUID;

public class SelectedCourses {
    private UUID id;
    private Student student;
    private Semester semester;
    private SpecialCourse specialCourse;

    public SelectedCourses() {
    }

    public SelectedCourses(UUID id, Student student, Semester semester, SpecialCourse specialCourse) {
        this.id = id;
        this.student = student;
        this.semester = semester;
        this.specialCourse = specialCourse;
    }

    public SelectedCourses(Student student, Semester semester, SpecialCourse specialCourse) {
        this.student = student;
        this.semester = semester;
        this.specialCourse = specialCourse;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public UUID getId() {
        return id;
    }
}
