package ru.urfu.mm.controller.recommendation;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.domain.exception.NotImplementedException;

@Tag(name = "Recommendations", description = "Рекомендации")
@RequestMapping(Endpoints.Recommendation.BASE)
public interface RecommendationsControllerDescription {
    @GetMapping
    RecommendationResultDTO calculateRecommendations();
}
