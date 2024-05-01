package ru.urfu.mm.application.usecase.create_course;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.domain.Course;

import java.util.UUID;

/**
 * Создание курса.
 * 1. Находим модуль. Если модуль не найден, то кидем исключение.
 * 2. Добавляем в модуль курс.
 * 3. Сохраняем курс.
 * 4. Сохраняем модуль.
 */
public class CreateCourse {
    private final ModuleGateway moduleGateway;
    private final CourseGateway courseGateway;

    public CreateCourse(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        this.moduleGateway = moduleGateway;
        this.courseGateway = courseGateway;
    }

    public void createCourse(CreateCourseRequest request) {
        var module = moduleGateway.getById(request.moduleId())
                .orElseThrow(() -> new ModuleNotFoundException(request.moduleId()));

        var course = new Course(
                UUID.randomUUID(),
                request.name(),
                request.credits(),
                request.controlTypes(),
                request.department(),
                request.teacher()
        );
        course.setDescription(request.description());
        module.addCourse(course);

        courseGateway.save(module, course);
//        moduleGateway.save(module);
    }
}
