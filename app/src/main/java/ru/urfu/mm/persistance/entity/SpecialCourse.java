package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;
import ru.urfu.mm.persistance.entity.enums.Control;

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
    @JoinColumn(name = "educational_modules_id")
    private EducationalModuleEntity module;

    public SpecialCourse() {
    }

    public SpecialCourse(
            UUID id,
            String name,
            int creditsCount,
            Control control,
            String description,
            String department,
            String teacherName,
            EducationalModuleEntity module) {
        this.id = id;
        this.name = name;
        this.creditsCount = creditsCount;
        this.control = control;
        this.description = description;
        this.department = department;
        this.teacherName = teacherName;
        this.module = module;
    }

    public SpecialCourse(
            String name,
            int creditsCount,
            Control control,
            String description,
            String department,
            String teacherName,
            EducationalModuleEntity module) {
        this.name = name;
        this.creditsCount = creditsCount;
        this.control = control;
        this.description = description;
        this.department = department;
        this.teacherName = teacherName;
        this.module = module;
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

    public EducationalModuleEntity getEducationalModule() {
        return module;
    }

    public void setEducationalModule(EducationalModuleEntity educationalModuleEntity) {
        this.module = educationalModuleEntity;
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
