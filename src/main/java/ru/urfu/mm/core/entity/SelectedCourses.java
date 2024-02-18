package ru.urfu.mm.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
@Table(name = "selected_courses")
public class SelectedCourses {
    @Id
    @Column
    private UUID id;
    @Column
    private String studentLogin;
    @Column
    // [ForeignKey("Semester")]
    private UUID semesterId;
    @Column
    // [ForeignKey("SpecialCourse")]
    private UUID specialCourseId;

//    public StudentModel Student { get; set; }
//    public SemesterModel Semester { get; set; }
//    public SpecialCourseModel SpecialCourse { get; set; }
}
