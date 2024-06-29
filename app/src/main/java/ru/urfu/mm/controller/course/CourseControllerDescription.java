package ru.urfu.mm.controller.course;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.controller.Endpoints;

@Tag(name = "Course", description = "Управление курсами")
@RequestMapping(Endpoints.Course.BASE)
public interface CourseControllerDescription {
    @Operation(summary = "Создать новый курс")
    @PostMapping(Endpoints.Course.CREATE)
    void createModuleCourse(@RequestBody CreateModuleCourseDTO dto);
}
