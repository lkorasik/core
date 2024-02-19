package ru.urfu.mm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.core.dto.CoursesBySemesterDTO;
import ru.urfu.mm.core.dto.GetCoursesDTO;
import ru.urfu.mm.core.dto.GetSelectedCoursesDTO;
import ru.urfu.mm.core.entity.Student;
import ru.urfu.mm.core.service.CourseForEducationalProgram;
import ru.urfu.mm.core.service.SpecialCourseService;
import ru.urfu.mm.core.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/specialCourses")
public class SpecialCourseController {
    @Autowired
    private SpecialCourseService specialCourseService;
    @Autowired
    private StudentService studentService;

    @PostMapping
    public List<CourseForEducationalProgram> specialCourse(@RequestBody GetCoursesDTO getCoursesDTO) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Student student = studentService.getStudent(authentication.getName());

        return specialCourseService
                .getCoursesByEducationalProgramAndSemesters(student.getEducationalProgram().getId(), getCoursesDTO.getSemesterIds());
    }

    @PostMapping("/selected")
    public List<CoursesBySemesterDTO> selected(@RequestBody GetSelectedCoursesDTO getSelectedCoursesDTO) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Student student = studentService.getStudent(authentication.getName());

        var selected = specialCourseService.getSelectedCoursesIds(student.getLogin(), getSelectedCoursesDTO.getSemestersIds());

        var result = new ArrayList<CoursesBySemesterDTO>();
        for(var key : selected.keySet()) {
            var item = new CoursesBySemesterDTO(key, selected.get(key));
            result.add(item);
        }
        return result;
    }
}
