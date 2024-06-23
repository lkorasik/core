package ru.urfu.mm.gateway;

import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.*;

@Component
public class StudyPlanGatewayImpl implements StudyPlanGateway {
    @Override
    public void save(BaseSyllabus studentSyllabus, EducationalProgram educationalProgram) {
        throw new ru.urfu.mm.application.exception.NotImplementedException();
    }
}
