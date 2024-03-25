package ru.urfu.mm.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.applicationlegacy.usecase.*;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.entity.Control;
import ru.urfu.mm.entity.Semester;
import ru.urfu.mm.service.CourseService;
import ru.urfu.mm.service.ModelConverterHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController extends AbstractAuthorizedController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private GetAllCourses getAllCourses;
    @Autowired
    private GetEducationalModuleCourses getEducationalModuleCourses;
    @Autowired
    private GetSelectedCoursesIds getSelectedCoursesIds;
    @Autowired
    private SelectCourses selectCourses;
    @Autowired
    private GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters;
    @Autowired
    private EditModuleSpecialCourse editModuleSpecialCourse;
    @Autowired
    private DeleteCourse deleteCourse;

    @PostMapping
    public List<CourseForProgramDTO> specialCourse(@RequestBody GetCoursesDTO getCoursesDTO) {
        return getCoursesByEducationalProgramAndSemesters
                .getCoursesByEducationalProgramAndSemesters(UUID.fromString(getUserToken()), getCoursesDTO.semestersIds())
                .stream()
                .map(x -> new CourseForProgramDTO(
                        x.getId(),
                        x.getName(),
                        x.getCreditsCount(),
                        Control.values()[x.getControl().ordinal()],
                        x.getDescription(),
                        x.getSemesters()
                                .stream()
                                .map(y -> new Semester(
                                        y.getId(),
                                        y.getYear(),
                                        y.getSemesterNumber()
                                ))
                                .toList(),
                        x.getEducationalModuleId(),
                        x.getRequiredSemesterId()
                ))
                .toList();
    }

    @PostMapping("/selected")
    public List<CoursesBySemesterDTO> selected(@RequestBody GetSelectedCoursesDTO getSelectedCoursesDTO) {
        var selected = getSelectedCoursesIds.getSelectedCoursesIds(UUID.fromString(getUserToken()), getSelectedCoursesDTO.semestersIds());

        var result = new ArrayList<CoursesBySemesterDTO>();
        for (var key : selected.keySet()) {
            var item = new CoursesBySemesterDTO(key, selected.get(key));
            result.add(item);
        }
        return result;
    }

    @PostMapping("/select")
    public void select(@RequestBody SelectedCoursesDTO selectedCourses) {
        selectCourses.select(UUID.fromString(getUserToken()), selectedCourses.coursesBySemesters().stream().map(x -> Map.entry(x.semesterId(), x.coursesIds())).toList());
    }

    @GetMapping("/statistics")
    public List<CourseStatisticsDTO> getActualSpecialCoursesStatistics(@RequestParam List<UUID> semestersId) {
        return courseService.getActualSpecialCoursesStatistics(semestersId);
    }

    @GetMapping("/allCourses")
    public List<CourseDTO> getAllCourses() {
        return getAllCourses
                .getAllCourses()
                .stream()
                .map(ModelConverterHelper::toDomain)
                .toList();
    }

    @GetMapping("/moduleCourses")
    public List<CourseDTO> getEducationalModuleCourses(@RequestParam("moduleId") String moduleIdDTO) {
        return getEducationalModuleCourses
                .getEducationalModuleCourses(UUID.fromString(moduleIdDTO))
                .stream()
                .map(ModelConverterHelper::toDomain)
                .toList();
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
        deleteCourse.deleteCourse(courseIdDTO.courseId());
    }

    @PostMapping("/moduleCourses/edit")
    public void editModuleCourse(@RequestBody EditModuleCourseDTO editModuleCourseDTO) {
        editModuleSpecialCourse.editModuleSpecialCourse(
                editModuleCourseDTO.courseId(),
                editModuleCourseDTO.courseName(),
                editModuleCourseDTO.creditsCount(),
                ru.urfu.mm.domainlegacy.Control.values()[editModuleCourseDTO.control().ordinal()],
                editModuleCourseDTO.courseDescription(),
                editModuleCourseDTO.department(),
                editModuleCourseDTO.teacherName()
        );
    }
}