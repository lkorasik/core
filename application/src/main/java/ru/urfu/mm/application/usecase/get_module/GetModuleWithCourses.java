package ru.urfu.mm.application.usecase.get_module;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.ModuleGateway;

import java.util.List;
import java.util.UUID;

/**
 * Получить модуль и курсы по идентификатору модуля
 * 1. Получаем модуль. Если модуль не найден, то кидаем ошибку с информацией о том, что такого модуля нет.
 * 2. Получаем все курсы, которые завязаны на данный модуль.
 */
public class GetModuleWithCourses {
    private final ModuleGateway moduleGateway;
    private final CourseGateway courseGateway;

    public GetModuleWithCourses(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        this.moduleGateway = moduleGateway;
        this.courseGateway = courseGateway;
    }

    public ModuleWithCoursesResponse getModule(UUID moduleId) {
        List<CourseResponse> courses = courseGateway
                .getEducationalModuleCourses(moduleId)
                .stream()
                .map(x -> new CourseResponse(x.getId(), x.getName()))
                .toList();
        return moduleGateway
                .getById(moduleId)
                .map(x -> new ModuleWithCoursesResponse(x.getId(), x.getName(), courses))
                .orElseThrow(() -> new ModuleNotFoundException(moduleId));
    }
}
