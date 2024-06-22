package ru.urfu.mm.controller.recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.domain.exception.NotImplementedException;
import ru.urfu.mm.persistance.entity.StudentEntity;
import ru.urfu.mm.service.RecommendationsService;
import ru.urfu.mm.service.StudentService;

@RestController
@RequestMapping(Endpoints.Recommendation.BASE)
public class RecommendationsController extends AbstractAuthorizedController implements RecommendationsControllerDescription {
    @Autowired
    private RecommendationsService recommendationsService;
    @Autowired
    private StudentService studentService;

    @Override
    public RecommendationResultDTO calculateRecommendations() {
        throw new NotImplementedException();
//        StudentEntity studentEntity = studentService.getStudent(getUserToken());
//
//        return recommendationsService.calculateRecommendations(studentEntity);
    }
}
