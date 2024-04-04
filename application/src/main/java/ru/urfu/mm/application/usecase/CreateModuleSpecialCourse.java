package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.domain.Control;
import ru.urfu.mm.domain.SpecialCourse;

import java.util.UUID;

public class CreateModuleSpecialCourse {
    private final ModuleGateway moduleGateway;
    private final CourseGateway courseGateway;

    public CreateModuleSpecialCourse(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        this.moduleGateway = moduleGateway;
        this.courseGateway = courseGateway;
    }

    public void createModuleSpecialCourse(
            String courseName,
            int creditsCount,
            Control control,
            String courseDescription,
            UUID moduleId,
            String department,
            String teacherName) {
        var educationalModule = moduleGateway.find(moduleId);
        var course = new SpecialCourse(
                courseName,
                creditsCount,
                control,
                courseDescription,
                department,
                teacherName,
                educationalModule
        );
        courseGateway.save(course);
    }
}
