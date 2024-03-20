package ru.urfu.mm.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.service.CoursesSelectionService;
import ru.urfu.mm.service.CourseService;
import ru.urfu.mm.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController extends AbstractAuthorizedController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CoursesSelectionService coursesSelectionService;

    @PostMapping
    public List<CourseForProgramDTO> specialCourse(@RequestBody GetCoursesDTO getCoursesDTO) {
        Student student = studentService.getStudent(getUserToken());

        return courseService
                .getCoursesByEducationalProgramAndSemesters(student.getEducationalProgram().getId(), getCoursesDTO.semestersIds())
                .stream()
                .map(x -> new CourseForProgramDTO(
                        x.getId(),
                        x.getName(),
                        x.getCreditsCount(),
                        x.getControl(),
                        x.getDescription(),
                        x.getSemesters(),
                        x.getEducationalModuleId(),
                        x.getRequiredSemesterId()
                ))
                .toList();
    }

    @PostMapping("/selected")
    public List<CoursesBySemesterDTO> selected(@RequestBody GetSelectedCoursesDTO getSelectedCoursesDTO) {
        Student student = studentService.getStudent(getUserToken());

        var selected = courseService.getSelectedCoursesIds(student.getLogin(), getSelectedCoursesDTO.semestersIds());

        var result = new ArrayList<CoursesBySemesterDTO>();
        for (var key : selected.keySet()) {
            var item = new CoursesBySemesterDTO(key, selected.get(key));
            result.add(item);
        }
        return result;
    }

    @PostMapping("/select")
    public void select(@RequestBody SelectedCoursesDTO selectedCourses) {
        Student student = studentService.getStudent(getUserToken());

        coursesSelectionService.validateCoursesSelection(student.getLogin(), selectedCourses.coursesBySemesters());
        coursesSelectionService.saveCoursesSelection(student.getLogin(), selectedCourses.coursesBySemesters());
    }

    @GetMapping("/statistics")
    public List<CourseStatisticsDTO> getActualSpecialCoursesStatistics(@RequestParam List<UUID> semestersId) {
        return courseService.getActualSpecialCoursesStatistics(semestersId);
    }

    @GetMapping("/allCourses")
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/moduleCourses")
    public List<CourseDTO> getEducationalModuleCourses(@RequestParam("moduleId") String moduleIdDTO) {
        return courseService.getEducationalModuleCourses(UUID.fromString(moduleIdDTO));
    }

    @GetMapping("/course")
    public CourseDTO getCourseById(@RequestParam("courseId") UUID courseId) {
        return courseService.getCourse(courseId);
    }

    @PostMapping("/moduleCourses/create")
    public void createModuleCourse(@RequestBody CreateModuleCourseDTO createModuleCourseDTO) {
        courseService.createModuleSpecialCourse(createModuleCourseDTO);
    }

    @DeleteMapping("/delete")
    public void deleteSpecialCourse(@RequestBody CourseIdDTO courseIdDTO) {
        courseService.deleteCourse(courseIdDTO.courseId());
    }

    @PostMapping("/moduleCourses/edit")
    public void editModuleCourse(@RequestBody EditModuleCourseDTO editModuleCourseDTO) {
        courseService.editModuleSpecialCourse(editModuleCourseDTO);
    }
}