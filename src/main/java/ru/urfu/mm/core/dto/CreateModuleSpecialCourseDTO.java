package ru.urfu.mm.core.dto;

import ru.urfu.mm.core.entity.Control;

import java.util.UUID;

public class CreateModuleSpecialCourseDTO {
    private String courseName;
    private int creditsCount;
    private Control control;
    private String courseDescription;
    private UUID educationalModuleId;
    private String department;
    private String teacherName;

    public CreateModuleSpecialCourseDTO() {
    }

    public CreateModuleSpecialCourseDTO(String courseName, int creditsCount, Control control, String courseDescription, UUID educationalModuleId, String department, String teacherName) {
        this.courseName = courseName;
        this.creditsCount = creditsCount;
        this.control = control;
        this.courseDescription = courseDescription;
        this.educationalModuleId = educationalModuleId;
        this.department = department;
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCreditsCount() {
        return creditsCount;
    }

    public Control getControl() {
        return control;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public UUID getEducationalModuleId() {
        return educationalModuleId;
    }

    public String getDepartment() {
        return department;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
