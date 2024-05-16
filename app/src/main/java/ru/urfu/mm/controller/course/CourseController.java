package ru.urfu.mm.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.*;
import ru.urfu.mm.application.usecase.create_course.CreateCourse;
import ru.urfu.mm.application.usecase.create_course.CreateCourseRequest;
import ru.urfu.mm.application.usecase.load_available_courses.AvailableModuleResponse;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.domain.enums.ControlTypes;
import ru.urfu.mm.domain.enums.SemesterType;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.exception.NotImplementedException;
import ru.urfu.mm.persistance.entity.enums.Control;
import ru.urfu.mm.persistance.entity.Semester;
import ru.urfu.mm.service.ModelConverterHelper;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(Endpoints.Course.BASE)
public class CourseController extends AbstractAuthorizedController {
    @Autowired
    private GetAllCourses getAllCourses;
    @Autowired
    private GetEducationalModuleCourses getEducationalModuleCourses;
//    @Autowired
//    private GetSelectedCoursesIds getSelectedCoursesIds;
//    @Autowired
//    private SelectCourses selectCourses;
//    @Autowired
//    private GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters;
    @Autowired
    private EditModuleSpecialCourse editModuleSpecialCourse;
    @Autowired
    private DeleteCourse deleteCourse;
    @Autowired
    private CreateCourse createCourse;
    @Autowired
    private GetCourse getCourse;
//    @Autowired
//    private GetActualSpecialCoursesStatistics getActualSpecialCoursesStatistics;
    @Autowired
    private GetSpecialCourseStudentsCount getSpecialCourseStudentsCount;
//    @Autowired
//    private GetSelectedCoursesByStudentAndSemester getSelectedCoursesByStudentAndSemester;
//    @Autowired
//    private LoadAvailableCourses loadAvailableCourses;

    @PostMapping
    public List<CourseForProgramDTO> specialCourse(@RequestBody GetCoursesDTO getCoursesDTO) {
        throw new NotImplementedException();
//        return getCoursesByEducationalProgramAndSemesters
//                .getCoursesByEducationalProgramAndSemesters(UUID.fromString(getUserToken()), getCoursesDTO.semestersIds())
//                .stream()
//                .map(x -> new CourseForProgramDTO(
//                        x.getId(),
//                        x.getName(),
//                        x.getCreditsCount(),
//                        Control.values()[x.getControl().ordinal()],
//                        x.getDescription(),
//                        x.getSemesters()
//                                .stream()
//                                .map(y -> new Semester(
//                                        y.getId(),
//                                        y.getYear(),
//                                        semesterTypeToEntityMapper.map(y.getType())
//                                ))
//                                .toList(),
//                        x.getEducationalModuleId(),
//                        x.getRequiredSemesterId()
//                ))
//                .toList();
    }

    @PostMapping(Endpoints.Course.SELECTED)
    public List<CoursesBySemesterDTO> selected(@RequestBody GetSelectedCoursesDTO getSelectedCoursesDTO) {
        throw new NotImplementedException();
//        var selected = getSelectedCoursesIds.getSelectedCoursesIds(UUID.fromString(getUserToken()), getSelectedCoursesDTO.semestersIds());
//
//        var result = new ArrayList<CoursesBySemesterDTO>();
//        for (var key : selected.keySet()) {
//            var item = new CoursesBySemesterDTO(key, selected.get(key));
//            result.add(item);
//        }
//        return result;
    }

    @PostMapping(Endpoints.Course.SELECT)
    public void select(@RequestBody SelectedCoursesDTO selectedCourses) {
        throw new NotImplementedException();
//        selectCourses.selectCourses(UUID.fromString(getUserToken()), selectedCourses.coursesBySemesters().stream().map(x -> Map.entry(x.semesterId(), x.coursesIds())).toList());
    }

    @GetMapping(Endpoints.Course.STATISTICS)
    public List<CourseStatisticsDTO> getActualSpecialCoursesStatistics(@RequestParam List<UUID> semestersId) {
        throw new NotImplementedException();
//        return getActualSpecialCoursesStatistics
//                .getActualSpecialCoursesStatistics(semestersId)
//                .stream()
//                .map(x -> new CourseStatisticsDTO(
//                                x.getSpecialCourse().getId(),
//                                x.getSpecialCourse().getName(),
//                                getSpecialCourseStudentsCount.specialCourseStudentsCount(x.getSpecialCourse().getId())
//                        )
//                )
//                .toList();
    }

    @GetMapping(Endpoints.Course.ALL_COURSES)
    public List<CourseDTO> getAllCourses() {
        throw new NotImplementedException();
//        return getAllCourses
//                .getAllCourses()
//                .stream()
//                .map(ModelConverterHelper::toDomain)
//                .toList();
    }

    @GetMapping(Endpoints.Course.MODULE_COURSES)
    public List<CourseDTO> getEducationalModuleCourses(@RequestParam("moduleId") String moduleIdDTO) {
        throw new NotImplementedException();
//        return getEducationalModuleCourses
//                .getEducationalModuleCourses(UUID.fromString(moduleIdDTO))
//                .stream()
//                .map(ModelConverterHelper::toDomain)
//                .toList();
    }

    @GetMapping(Endpoints.Course.COURSE)
    public CourseDTO getCourseById(@RequestParam("id") UUID courseId) {
        throw new NotImplementedException();
//        Course course = getCourse.getCourse(courseId);
//        return new CourseDTO(
//                course.getId(),
//                course.getName(),
//                course.getCredits(),
//                Control.values()[course.getControl().ordinal()],
//                course.getDescription(),
//                course.getEducationalModule().getId(),
//                course.getTeacher(),
//                course.getDepartment()
//        );
    }

    @PostMapping(Endpoints.Course.CREATE)
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

    @DeleteMapping(Endpoints.Course.DELETE)
    public void deleteSpecialCourse(@RequestBody CourseIdDTO courseIdDTO) {
        deleteCourse.deleteCourse(courseIdDTO.courseId());
    }

    @PostMapping(Endpoints.Course.MODULE_COURSES_EDIT)
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

    @GetMapping(Endpoints.Course.SELECTED_COURSE_NAME)
    public List<SelectedCourseNameDTO> getSelectedCourseNamesBySemester(@RequestBody GetSelectedCoursesBySemesterDTO dto) {
        throw new NotImplementedException();
//        UUID studentId = UUID.fromString(getUserToken());
//        return getSelectedCoursesByStudentAndSemester
//                .getSelectedCoursesByStudentAndSemester(studentId, UUID.fromString(dto.semesterId()))
//                .stream()
//                .map(x -> new SelectedCourseNameDTO(
//                        x.getSpecialCourse().getName(),
//                        x.isRequiredCourse(),
//                        x.getSpecialCourse().getId())
//                )
//                .toList();
    }

    @GetMapping(Endpoints.Course.AVAILABLE)
    public List<AvailableModuleResponse> loadAvailableCourses() {
        throw new NotImplementedException();
//        return loadAvailableCourses.loadAvailableCourses(UUID.fromString(getUserToken()));
    }
}