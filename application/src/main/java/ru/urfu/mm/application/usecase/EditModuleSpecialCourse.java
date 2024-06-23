package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.enums.ControlTypes;
import ru.urfu.mm.domain.exception.NotImplementedException;

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
            ControlTypes controlTypes,
            String courseDescription,
            String department,
            String teacherName) {
        throw new NotImplementedException();
    }
}
