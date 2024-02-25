package ru.urfu.mm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.core.dto.RecommendationResultDTO;
import ru.urfu.mm.core.entity.Student;
import ru.urfu.mm.core.service.RecommendationsService;
import ru.urfu.mm.core.service.StudentService;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationsController {
    @Autowired
    private RecommendationsService recommendationsService;
    @Autowired
    private StudentService studentService;

    @GetMapping
    public RecommendationResultDTO calculateRecommendations() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Student student = studentService.getStudent(authentication.getName());

        return recommendationsService.calculateRecommendations(student);
    }
}
