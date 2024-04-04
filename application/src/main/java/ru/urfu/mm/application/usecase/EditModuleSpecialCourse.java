package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.Control;

import java.util.UUID;

public class EditModuleSpecialCourse {
    private final CourseGateway courseGateway;

    public EditModuleSpecialCourse(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public void editModuleSpecialCourse(
            UUID courseId,
            String courseName,
            int creditsCount,
            Control control,
            String courseDescription,
            String department,
            String teacherName) {
        var oldCourseValue = courseGateway.getById(courseId);

        if (oldCourseValue != null) {
            oldCourseValue.setName(courseName);
            oldCourseValue.setDepartment(department);
            oldCourseValue.setTeacherName(teacherName);
            oldCourseValue.setControl(control);
            oldCourseValue.setCreditsCount(creditsCount);
            oldCourseValue.setDescription(courseDescription);

            courseGateway.save(oldCourseValue);
        }
    }
}
