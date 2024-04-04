package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.domain.Module;

import java.util.List;
import java.util.UUID;

public class CreateModuleWithCourses {
    private final ModuleGateway moduleGateway;
    private final CourseGateway courseGateway;

    public CreateModuleWithCourses(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        this.moduleGateway = moduleGateway;
        this.courseGateway = courseGateway;
    }

    public void createModuleWithCourses(String educationalModuleName, List<UUID> specialCoursesIds) {
        Module module = new Module(educationalModuleName);
        moduleGateway.save(module);

        for(var specialCourseId : specialCoursesIds) {
            var course = courseGateway.getById(specialCourseId);
            course.setEducationalModule(module);
            courseGateway.save(course);
        }
    }
}
