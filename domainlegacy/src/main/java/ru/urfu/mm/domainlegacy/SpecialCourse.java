package ru.urfu.mm.domainlegacy;

import java.util.UUID;

public class SpecialCourse {
    private UUID id;
    private String name;
    private int creditsCount;
    private Control control;
    private String description;
    private String department;
    private String teacherName;
    private java.lang.Module module;

    public SpecialCourse() {
    }

    public SpecialCourse(UUID id, String name, int creditsCount, Control control, String description, String department, String teacherName, java.lang.Module module) {
        this.id = id;
        this.name = name;
        this.creditsCount = creditsCount;
        this.control = control;
        this.description = description;
        this.department = department;
        this.teacherName = teacherName;
        this.module = module;
    }

    public SpecialCourse(String name, int creditsCount, Control control, String description, String department, String teacherName, java.lang.Module module) {
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

    public java.lang.Module getEducationalModule() {
        return module;
    }

    public void setEducationalModule(java.lang.Module module) {
        this.module = module;
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
