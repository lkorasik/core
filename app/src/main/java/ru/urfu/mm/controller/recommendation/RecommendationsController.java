package ru.urfu.mm.controller.recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.service.RecommendationsService;
import ru.urfu.mm.service.StudentService;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationsController extends AbstractAuthorizedController {
    @Autowired
    private RecommendationsService recommendationsService;
    @Autowired
    private StudentService studentService;

    @GetMapping
    public RecommendationResultDTO calculateRecommendations() {
        Student student = studentService.getStudent(getUserToken());

        return recommendationsService.calculateRecommendations(student);
    }
}
