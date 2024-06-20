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
    private SemesterEntity semesterEntity;
    @ManyToOne
    @JoinColumn(name = "special_course_id")
    private SpecialCourse specialCourse;

    public SelectedCourses() {
    }

    public SelectedCourses(UUID id, StudentEntity studentEntity, SemesterEntity semesterEntity, SpecialCourse specialCourse) {
        this.id = id;
        this.studentEntity = studentEntity;
        this.semesterEntity = semesterEntity;
        this.specialCourse = specialCourse;
    }

    public SelectedCourses(StudentEntity studentEntity, SemesterEntity semesterEntity, SpecialCourse specialCourse) {
        this.studentEntity = studentEntity;
        this.semesterEntity = semesterEntity;
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

    public SemesterEntity getSemester() {
        return semesterEntity;
    }

    public void setSemester(SemesterEntity semesterEntity) {
        this.semesterEntity = semesterEntity;
    }

    public UUID getId() {
        return id;
    }
}
