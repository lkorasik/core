package ru.urfu.mm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.core.entity.Student;
import ru.urfu.mm.core.service.StudentService;

@RestController
@RequestMapping("/api/educationalPrograms/")
public class EducationalProgramController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/current")
    public void current() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Student student = studentService.getStudent(authentication.getName());
    }
}
