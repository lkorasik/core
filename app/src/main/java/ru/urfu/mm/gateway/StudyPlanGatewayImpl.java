package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.StudyPlan;
import ru.urfu.mm.entity.Semester;
import ru.urfu.mm.entity.SemesterPlanEntity;
import ru.urfu.mm.entity.StudyPlanEntity;
import ru.urfu.mm.repository.EducationalProgramRepository;
import ru.urfu.mm.repository.SemesterPlanRepository;
import ru.urfu.mm.repository.SemesterRepository;
import ru.urfu.mm.repository.StudyPlanRepository;

import java.util.UUID;

@Component
public class StudyPlanGatewayImpl implements StudyPlanGateway {
    private final SemesterPlanRepository semesterPlanRepository;
    private final StudyPlanRepository studyPlanRepository;
    private final SemesterRepository semesterRepository;
    private final EducationalProgramRepository programRepository;

    @Autowired
    public StudyPlanGatewayImpl(
            SemesterPlanRepository semesterPlanRepository,
            StudyPlanRepository studyPlanRepository,
            SemesterRepository semesterRepository,
            EducationalProgramRepository programRepository) {
        this.semesterPlanRepository = semesterPlanRepository;
        this.studyPlanRepository = studyPlanRepository;
        this.semesterRepository = semesterRepository;
        this.programRepository = programRepository;
    }

    @Override
    public void save(StudyPlan studyPlan, Program program) {
        Semester firstSemesterEntity = semesterRepository
                .findById(studyPlan.getFirstSemesterPlan().getSemester().getId())
                .get();
        Semester secondSemesterEntity = semesterRepository
                .findById(studyPlan.getSecondSemesterPlan().getSemester().getId())
                .get();
        Semester thirdSemesterEntity = semesterRepository
                .findById(studyPlan.getThirdSemesterPlan().getSemester().getId())
                .get();
        Semester fourthSemesterEntity = semesterRepository
                .findById(studyPlan.getFourthSemesterPlan().getSemester().getId())
                .get();

        SemesterPlanEntity firstSemesterPlanEntity = new SemesterPlanEntity(
                studyPlan.getFirstSemesterPlan().getId(),
                firstSemesterEntity,
                studyPlan.getFirstSemesterPlan().getRecommendedCredits()
        );
        SemesterPlanEntity secondSemesterPlanEntity = new SemesterPlanEntity(
                studyPlan.getSecondSemesterPlan().getId(),
                secondSemesterEntity,
                studyPlan.getSecondSemesterPlan().getRecommendedCredits()
        );
        SemesterPlanEntity thirdSemesterPlanEntity = new SemesterPlanEntity(
                studyPlan.getThirdSemesterPlan().getId(),
                thirdSemesterEntity,
                studyPlan.getThirdSemesterPlan().getRecommendedCredits()
        );
        SemesterPlanEntity fourthSemesterPlanEntity = new SemesterPlanEntity(
                studyPlan.getFourthSemesterPlan().getId(),
                fourthSemesterEntity,
                studyPlan.getFourthSemesterPlan().getRecommendedCredits()
        );

        StudyPlanEntity studyPlanEntity = new StudyPlanEntity(
                UUID.randomUUID(),
                firstSemesterPlanEntity,
                secondSemesterPlanEntity,
                thirdSemesterPlanEntity,
                fourthSemesterPlanEntity,
                programRepository.findById(program.getId()).get()
        );

        semesterPlanRepository.save(firstSemesterPlanEntity);
        semesterPlanRepository.save(secondSemesterPlanEntity);
        semesterPlanRepository.save(thirdSemesterPlanEntity);
        semesterPlanRepository.save(fourthSemesterPlanEntity);

        studyPlanRepository.save(studyPlanEntity);
    }
}
