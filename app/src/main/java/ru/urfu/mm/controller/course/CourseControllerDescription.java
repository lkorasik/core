package ru.urfu.mm.controller.course;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.controller.Endpoints;

@Tag(name = "Course", description = "Управление курсами")
@RequestMapping(Endpoints.Course.BASE)
public interface CourseControllerDescription {
    @PostMapping(Endpoints.Course.CREATE)
    void createModuleCourse(@RequestBody CreateModuleCourseDTO dto);

    @DeleteMapping(Endpoints.Course.DELETE)
    void deleteSpecialCourse(@RequestBody CourseIdDTO courseIdDTO);

    @PostMapping(Endpoints.Course.MODULE_COURSES_EDIT)
    void editModuleCourse(@RequestBody EditModuleCourseDTO editModuleCourseDTO);
}
