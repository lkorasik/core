package ru.urfu.mm.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.*;
import ru.urfu.mm.application.usecase.create_course.CreateCourse;
import ru.urfu.mm.application.usecase.create_course.CreateCourseRequest;
import ru.urfu.mm.application.usecase.load_available_courses.AvailableModuleResponse;
import ru.urfu.mm.application.usecase.load_available_courses.LoadAvailableCourses;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.domain.ControlTypes;
import ru.urfu.mm.domain.SemesterType;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.entity.Control;
import ru.urfu.mm.entity.Semester;
import ru.urfu.mm.service.ModelConverterHelper;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController extends AbstractAuthorizedController {
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
    @Autowired
    private CreateCourse createCourse;
    @Autowired
    private GetCourse getCourse;
    @Autowired
    private GetActualSpecialCoursesStatistics getActualSpecialCoursesStatistics;
    @Autowired
    private GetSpecialCourseStudentsCount getSpecialCourseStudentsCount;
    @Autowired
    private GetSelectedCoursesByStudentAndSemester getSelectedCoursesByStudentAndSemester;
    @Autowired
    private LoadAvailableCourses loadAvailableCourses;
    @Autowired
    private Mapper<SemesterType, ru.urfu.mm.entity.SemesterType> semesterTypeToEntityMapper;

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
                                        semesterTypeToEntityMapper.map(y.getType())
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
        selectCourses.selectCourses(UUID.fromString(getUserToken()), selectedCourses.coursesBySemesters().stream().map(x -> Map.entry(x.semesterId(), x.coursesIds())).toList());
    }

    @GetMapping("/statistics")
    public List<CourseStatisticsDTO> getActualSpecialCoursesStatistics(@RequestParam List<UUID> semestersId) {
        return getActualSpecialCoursesStatistics
                .getActualSpecialCoursesStatistics(semestersId)
                .stream()
                .map(x -> new CourseStatisticsDTO(
                                x.getSpecialCourse().getId(),
                                x.getSpecialCourse().getName(),
                                getSpecialCourseStudentsCount.specialCourseStudentsCount(x.getSpecialCourse().getId())
                        )
                )
                .toList();
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
    public CourseDTO getCourseById(@RequestParam("id") UUID courseId) {
        Course course = getCourse.getCourse(courseId);
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getCreditsCount(),
                Control.values()[course.getControl().ordinal()],
                course.getDescription(),
                course.getEducationalModule().getId(),
                course.getTeacherName(),
                course.getDepartment()
        );
    }

    @PostMapping("/create")
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
                ControlTypes.values()[editModuleCourseDTO.control().ordinal()],
                editModuleCourseDTO.courseDescription(),
                editModuleCourseDTO.department(),
                editModuleCourseDTO.teacherName()
        );
    }

    @GetMapping("/selectedCourseName")
    public List<SelectedCourseNameDTO> getSelectedCourseNamesBySemester(@RequestBody GetSelectedCoursesBySemesterDTO dto) {
        UUID studentId = UUID.fromString(getUserToken());
        return getSelectedCoursesByStudentAndSemester
                .getSelectedCoursesByStudentAndSemester(studentId, UUID.fromString(dto.semesterId()))
                .stream()
                .map(x -> new SelectedCourseNameDTO(
                        x.getSpecialCourse().getName(),
                        x.isRequiredCourse(),
                        x.getSpecialCourse().getId())
                )
                .toList();
    }

    @GetMapping("/available")
    public List<AvailableModuleResponse> loadAvailableCourses() {
        return loadAvailableCourses.loadAvailableCourses(UUID.fromString(getUserToken()));
    }
}