package ru.urfu.mm.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.*;
import ru.urfu.mm.application.usecase.create_course.CreateCourse;
import ru.urfu.mm.application.usecase.create_course.CreateCourseRequest;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.domain.enums.ControlTypes;

@RestController
@RequestMapping(Endpoints.Course.BASE)
public class CourseController extends AbstractAuthorizedController implements CourseControllerDescription {
    @Autowired
    private EditModuleSpecialCourse editModuleSpecialCourse;
    @Autowired
    private DeleteCourse deleteCourse;
    @Autowired
    private CreateCourse createCourse;

    @Override
    public void createModuleCourse(@RequestBody CreateModuleCourseDTO dto) {
        CreateCourseRequest request = new CreateCourseRequest(
                dto.name(),
                dto.credits(),
                ControlTypes.values()[dto.controlType().ordinal()],
                dto.description(),
                dto.moduleId(),
                dto.department(),
                dto.teacher()
        );
        createCourse.createCourse(request);
    }

    @Override
    public void deleteSpecialCourse(@RequestBody CourseIdDTO courseIdDTO) {
        deleteCourse.deleteCourse(courseIdDTO.courseId());
    }

    @Override
    public void editModuleCourse(@RequestBody EditModuleCourseDTO editModuleCourseDTO) {
        editModuleSpecialCourse.editModuleSpecialCourse(
                editModuleCourseDTO.courseId(),
                editModuleCourseDTO.courseName(),
                editModuleCourseDTO.creditsCount(),
                ControlTypes.values()[editModuleCourseDTO.control().ordinal()],
                editModuleCourseDTO.courseDescription(),
                editModuleCourseDTO.department(),
                editModuleCourseDTO.teacherName()
        );
    }
}