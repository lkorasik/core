package ru.urfu.mm.dto;

import ru.urfu.mm.entity.Control;

import java.util.UUID;

public class EditModuleSpecialCourseDTO {
    private UUID specialCourseId;
    private String courseName;
    private int creditsCount;
    private Control control;
    private String courseDescription;
    private String department;
    private String teacherName;

    public EditModuleSpecialCourseDTO() {

    }

    public EditModuleSpecialCourseDTO(UUID specialCourseId, String courseName, int creditsCount, Control control, String courseDescription, String department, String teacherName) {
        this.specialCourseId = specialCourseId;
        this.courseName = courseName;
        this.creditsCount = creditsCount;
        this.control = control;
        this.courseDescription = courseDescription;
        this.department = department;
        this.teacherName = teacherName;
    }

    public UUID getSpecialCourseId() {
        return specialCourseId;
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

    public String getDepartment() {
        return department;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
