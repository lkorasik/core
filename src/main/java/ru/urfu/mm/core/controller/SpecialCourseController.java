package ru.urfu.mm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.core.dto.GetCoursesDTO;
import ru.urfu.mm.core.entity.Student;
import ru.urfu.mm.core.service.CourseForEducationalProgram;
import ru.urfu.mm.core.service.SpecialCourseService;
import ru.urfu.mm.core.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/specialCourses")
public class SpecialCourseController {
    @Autowired
    private SpecialCourseService specialCourseService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public List<CourseForEducationalProgram> specialCourse(@RequestBody GetCoursesDTO getCoursesDTO) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Student student = studentService.getStudent(authentication.getName());

        return specialCourseService
                .getCoursesByEducationalProgramAndSemesters(student.getEducationalProgram().getId(), getCoursesDTO.getSemesterIds());
    }
}
