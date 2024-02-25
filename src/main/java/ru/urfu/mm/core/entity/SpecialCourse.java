package ru.urfu.mm.core.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "special_courses")
public class SpecialCourse {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private String name;
    @Column
    private int creditsCount;
    @Column
    private Control control;
    @Column
    private String description;
    @Column
    private String department;
    @Column
    private String teacherName;
    @ManyToOne
    @JoinColumn(name = "educational_module_id")
    private EducationalModule educationalModule;

    public SpecialCourse() {
    }

    public SpecialCourse(UUID id, String name, int creditsCount, Control control, String description, String department, String teacherName, EducationalModule educationalModule) {
        this.id = id;
        this.name = name;
        this.creditsCount = creditsCount;
        this.control = control;
        this.description = description;
        this.department = department;
        this.teacherName = teacherName;
        this.educationalModule = educationalModule;
    }

    public SpecialCourse(String name, int creditsCount, Control control, String description, String department, String teacherName, EducationalModule educationalModule) {
        this.name = name;
        this.creditsCount = creditsCount;
        this.control = control;
        this.description = description;
        this.department = department;
        this.teacherName = teacherName;
        this.educationalModule = educationalModule;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCreditsCount() {
        return creditsCount;
    }

    public Control getControl() {
        return control;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartment() {
        return department;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public EducationalModule getEducationalModule() {
        return educationalModule;
    }

    public void setEducationalModule(EducationalModule educationalModule) {
        this.educationalModule = educationalModule;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreditsCount(int creditsCount) {
        this.creditsCount = creditsCount;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
