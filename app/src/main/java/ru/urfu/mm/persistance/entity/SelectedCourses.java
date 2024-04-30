package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Deprecated
@Entity
@Table(name = "selected_courses")
public class SelectedCourses {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "student_login")
    private StudentEntity studentEntity;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;
    @ManyToOne
    @JoinColumn(name = "special_course_id")
    private SpecialCourse specialCourse;

    public SelectedCourses() {
    }

    public SelectedCourses(UUID id, StudentEntity studentEntity, Semester semester, SpecialCourse specialCourse) {
        this.id = id;
        this.studentEntity = studentEntity;
        this.semester = semester;
        this.specialCourse = specialCourse;
    }

    public SelectedCourses(StudentEntity studentEntity, Semester semester, SpecialCourse specialCourse) {
        this.studentEntity = studentEntity;
        this.semester = semester;
        this.specialCourse = specialCourse;
    }

    public StudentEntity getStudent() {
        return studentEntity;
    }

    public void setStudent(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
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
