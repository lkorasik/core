package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.applicationlegacy.gateway.ModuleGateway;
import ru.urfu.mm.domainlegacy.Control;
import ru.urfu.mm.domainlegacy.SpecialCourse;

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
