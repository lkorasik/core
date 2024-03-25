package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.applicationlegacy.gateway.ModuleGateway;

import java.util.UUID;

public class DeleteModuleById {
    private final ModuleGateway moduleGateway;
    private final CourseGateway courseGateway;

    public DeleteModuleById(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        this.moduleGateway = moduleGateway;
        this.courseGateway = courseGateway;
    }

    public void deleteModuleById(UUID educationalModuleId) {
        var module = moduleGateway.find(educationalModuleId);
        var courses = courseGateway.getEducationalModuleCourses(educationalModuleId);

        for(var course: courses) {
            course.setEducationalModule(null);
//            courseGateway.save(course);
        }
        moduleGateway.delete(module);
    }
}
