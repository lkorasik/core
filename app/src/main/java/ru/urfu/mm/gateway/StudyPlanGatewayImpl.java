package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.domain.exception.NotImplementedException;
import ru.urfu.mm.persistance.entity.Semester;
import ru.urfu.mm.persistance.entity.SemesterPlanEntity;
import ru.urfu.mm.persistance.entity.SyllabusEntity;
import ru.urfu.mm.persistance.repository.ProgramRepository;
import ru.urfu.mm.persistance.repository.SemesterPlanRepository;
import ru.urfu.mm.persistance.repository.SemesterRepository;
import ru.urfu.mm.persistance.repository.StudyPlanRepository;

import java.util.List;
import java.util.UUID;

@Component
public class StudyPlanGatewayImpl implements StudyPlanGateway {
    private final SemesterPlanRepository semesterPlanRepository;
    private final StudyPlanRepository studyPlanRepository;
    private final SemesterRepository semesterRepository;
    private final ProgramRepository programRepository;

    @Autowired
    public StudyPlanGatewayImpl(
            SemesterPlanRepository semesterPlanRepository,
            StudyPlanRepository studyPlanRepository,
            SemesterRepository semesterRepository,
            ProgramRepository programRepository) {
        this.semesterPlanRepository = semesterPlanRepository;
        this.studyPlanRepository = studyPlanRepository;
        this.semesterRepository = semesterRepository;
        this.programRepository = programRepository;
    }

    @Override
    public void save(Syllabus syllabus, EducationalProgram educationalProgram) {
        Semester firstSemesterEntity = semesterRepository
                .findById(syllabus.getFirstSemesterPlan().getSemester().getId())
                .get();
        Semester secondSemesterEntity = semesterRepository
                .findById(syllabus.getSecondSemesterPlan().getSemester().getId())
                .get();
        Semester thirdSemesterEntity = semesterRepository
                .findById(syllabus.getThirdSemesterPlan().getSemester().getId())
                .get();
        Semester fourthSemesterEntity = semesterRepository
                .findById(syllabus.getFourthSemesterPlan().getSemester().getId())
                .get();

        SemesterPlanEntity firstSemesterPlanEntity = new SemesterPlanEntity(
                syllabus.getFirstSemesterPlan().getId(),
                firstSemesterEntity,
                syllabus.getFirstSemesterPlan().getRecommendedCredits()
        );
        SemesterPlanEntity secondSemesterPlanEntity = new SemesterPlanEntity(
                syllabus.getSecondSemesterPlan().getId(),
                secondSemesterEntity,
                syllabus.getSecondSemesterPlan().getRecommendedCredits()
        );
        SemesterPlanEntity thirdSemesterPlanEntity = new SemesterPlanEntity(
                syllabus.getThirdSemesterPlan().getId(),
                thirdSemesterEntity,
                syllabus.getThirdSemesterPlan().getRecommendedCredits()
        );
        SemesterPlanEntity fourthSemesterPlanEntity = new SemesterPlanEntity(
                syllabus.getFourthSemesterPlan().getId(),
                fourthSemesterEntity,
                syllabus.getFourthSemesterPlan().getRecommendedCredits()
        );

        SyllabusEntity syllabusEntity = new SyllabusEntity(
                UUID.randomUUID(),
                firstSemesterPlanEntity,
                secondSemesterPlanEntity,
                thirdSemesterPlanEntity,
                fourthSemesterPlanEntity,
                programRepository.findById(educationalProgram.getId()).get()
        );

        semesterPlanRepository.save(firstSemesterPlanEntity);
        semesterPlanRepository.save(secondSemesterPlanEntity);
        semesterPlanRepository.save(thirdSemesterPlanEntity);
        semesterPlanRepository.save(fourthSemesterPlanEntity);

        studyPlanRepository.save(syllabusEntity);
    }

    @Override
    public List<Syllabus> findAllByProgram(EducationalProgram educationalProgram) {
        throw new NotImplementedException();
    }
}
