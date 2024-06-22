package ru.urfu.mm.controller.course;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.load_available_courses.AvailableModuleResponse;
import ru.urfu.mm.controller.Endpoints;

import java.util.List;
import java.util.UUID;

@Tag(name = "Course", description = "Управление курсами")
@RequestMapping(Endpoints.Course.BASE)
public interface CourseControllerDescription {
    @PostMapping
    List<CourseForProgramDTO> specialCourse(@RequestBody GetCoursesDTO getCoursesDTO);

    @PostMapping(Endpoints.Course.SELECTED)
    List<CoursesBySemesterDTO> selected(@RequestBody GetSelectedCoursesDTO getSelectedCoursesDTO);

    @PostMapping(Endpoints.Course.SELECT)
    void select(@RequestBody SelectedCoursesDTO selectedCourses);

    @GetMapping(Endpoints.Course.STATISTICS)
    List<CourseStatisticsDTO> getActualSpecialCoursesStatistics(@RequestParam List<UUID> semestersId);

    @GetMapping(Endpoints.Course.ALL_COURSES)
    List<CourseDTO> getAllCourses();

    @GetMapping(Endpoints.Course.MODULE_COURSES)
    List<CourseDTO> getEducationalModuleCourses(@RequestParam("moduleId") String moduleIdDTO);

    @GetMapping(Endpoints.Course.COURSE)
    CourseDTO getCourseById(@RequestParam("id") UUID courseId);

    @PostMapping(Endpoints.Course.CREATE)
    void createModuleCourse(@RequestBody CreateModuleCourseDTO dto);

    @DeleteMapping(Endpoints.Course.DELETE)
    void deleteSpecialCourse(@RequestBody CourseIdDTO courseIdDTO);

    @PostMapping(Endpoints.Course.MODULE_COURSES_EDIT)
    void editModuleCourse(@RequestBody EditModuleCourseDTO editModuleCourseDTO);

    @GetMapping(Endpoints.Course.SELECTED_COURSE_NAME)
    List<SelectedCourseNameDTO> getSelectedCourseNamesBySemester(@RequestBody GetSelectedCoursesBySemesterDTO dto);

    @GetMapping(Endpoints.Course.AVAILABLE)
    List<AvailableModuleResponse> loadAvailableCourses();
}
