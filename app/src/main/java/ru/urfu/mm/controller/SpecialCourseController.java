package ru.urfu.mm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.dto.*;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.service.CourseForEducationalProgram;
import ru.urfu.mm.service.CoursesSelectionService;
import ru.urfu.mm.service.SpecialCourseService;
import ru.urfu.mm.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/specialCourses")
public class SpecialCourseController {
    @Autowired
    private SpecialCourseService specialCourseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CoursesSelectionService coursesSelectionService;

    @PostMapping
    public List<CourseForEducationalProgram> specialCourse(@RequestBody GetCoursesDTO getCoursesDTO) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Student student = studentService.getStudent(authentication.getName());

        return specialCourseService
                .getCoursesByEducationalProgramAndSemesters(student.getEducationalProgram().getId(), getCoursesDTO.getSemestersIds());
    }

    @PostMapping("/selected")
    public List<CoursesBySemesterDTO> selected(@RequestBody GetSelectedCoursesDTO getSelectedCoursesDTO) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Student student = studentService.getStudent(authentication.getName());

        var selected = specialCourseService.getSelectedCoursesIds(student.getLogin(), getSelectedCoursesDTO.getSemestersIds());

        var result = new ArrayList<CoursesBySemesterDTO>();
        for (var key : selected.keySet()) {
            var item = new CoursesBySemesterDTO(key, selected.get(key));
            result.add(item);
        }
        return result;
    }

    @PostMapping("/select")
    public void select(@RequestBody SelectedCoursesDTO selectedCourses) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Student student = studentService.getStudent(authentication.getName());

        coursesSelectionService.validateCoursesSelection(student.getLogin(), selectedCourses.getCoursesBySemesters());
        coursesSelectionService.saveCoursesSelection(student.getLogin(), selectedCourses.getCoursesBySemesters());
    }

    @GetMapping("/statistics")
    public List<SpecialCourseStatisticsDTO> getActualSpecialCoursesStatistics(@RequestParam List<UUID> semestersId) {
        return specialCourseService.getActualSpecialCoursesStatistics(semestersId);
    }

    @GetMapping("/allCourses")
    public List<SpecialCourseDTO> getAllCourses() {
        return specialCourseService.getAllCourses();
    }

    @GetMapping("/educationalModuleCourses")
    public List<SpecialCourseDTO> getEducationalModuleCourses(@RequestParam("educationalModuleId") String moduleIdDTO) {
        return specialCourseService.getEducationalModuleCourses(UUID.fromString(moduleIdDTO));
    }

    @GetMapping("/course")
    public SpecialCourseDTO getCourseById(@RequestParam("specialCourseId") UUID specialCourseId) {
        return specialCourseService.getCourse(specialCourseId);
    }

    @PostMapping("/educationalModuleCourses/create")
    public void createModuleCourse(@RequestBody CreateModuleSpecialCourseDTO createModuleSpecialCourseDTO) {
        specialCourseService.createModuleSpecialCourse(createModuleSpecialCourseDTO);
    }

    @DeleteMapping("/delete")
    public void deleteSpecialCourse(@RequestBody CourseIdDTO courseIdDTO) {
        specialCourseService.deleteCourse(courseIdDTO.getSpecialCourseId());
    }

    @PostMapping("/educationalModuleCourses/edit")
    public void editModuleCourse(@RequestBody EditModuleSpecialCourseDTO editModuleSpecialCourseDTO) {
        specialCourseService.editModuleSpecialCourse(editModuleSpecialCourseDTO);
    }
}