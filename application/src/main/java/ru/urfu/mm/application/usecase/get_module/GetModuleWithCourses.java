package ru.urfu.mm.application.usecase.get_module;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.application.usecase.get_token.GetTokensForGroupResponse;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.EducationalModule;

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
        EducationalModule educationalModule = moduleGateway.getById(moduleId).get();
        List<Course> list = educationalModule.getCourses();
        return new ModuleWithCoursesResponse(
                educationalModule.getId(),
                educationalModule.getName(),
                list.stream()
                        .map(x -> new CourseResponse(x.getId(), x.getName()))
                        .toList()
        ) ;
    }
}
