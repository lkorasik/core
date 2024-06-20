package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.domain.exception.NotImplementedException;
import ru.urfu.mm.persistance.repository.ProgramRepository;
import ru.urfu.mm.persistance.repository.SemesterPlanRepository;
import ru.urfu.mm.persistance.repository.SemesterRepository;
import ru.urfu.mm.persistance.repository.StudyPlanRepository;

import java.util.List;

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
    public void save(StudentSyllabus studentSyllabus, EducationalProgram educationalProgram) {
        throw new ru.urfu.mm.application.exception.NotImplementedException();
//        Semester firstSemesterEntity = semesterRepository
//                .findById(studentSyllabus.getFirstSemesterPlan().getSemester().getId())
//                .get();
//        Semester secondSemesterEntity = semesterRepository
//                .findById(studentSyllabus.getSecondSemesterPlan().getSemester().getId())
//                .get();
//        Semester thirdSemesterEntity = semesterRepository
//                .findById(studentSyllabus.getThirdSemesterPlan().getSemester().getId())
//                .get();
//        Semester fourthSemesterEntity = semesterRepository
//                .findById(studentSyllabus.getFourthSemesterPlan().getSemester().getId())
//                .get();
//
//        SemesterPlanEntity firstSemesterPlanEntity = new SemesterPlanEntity(
//                studentSyllabus.getFirstSemesterPlan().getId(),
//                firstSemesterEntity,
//                studentSyllabus.getFirstSemesterPlan().getRecommendedCredits()
//        );
//        SemesterPlanEntity secondSemesterPlanEntity = new SemesterPlanEntity(
//                studentSyllabus.getSecondSemesterPlan().getId(),
//                secondSemesterEntity,
//                studentSyllabus.getSecondSemesterPlan().getRecommendedCredits()
//        );
//        SemesterPlanEntity thirdSemesterPlanEntity = new SemesterPlanEntity(
//                studentSyllabus.getThirdSemesterPlan().getId(),
//                thirdSemesterEntity,
//                studentSyllabus.getThirdSemesterPlan().getRecommendedCredits()
//        );
//        SemesterPlanEntity fourthSemesterPlanEntity = new SemesterPlanEntity(
//                studentSyllabus.getFourthSemesterPlan().getId(),
//                fourthSemesterEntity,
//                studentSyllabus.getFourthSemesterPlan().getRecommendedCredits()
//        );
//
//        SyllabusEntity syllabusEntity = new SyllabusEntity(
//                UUID.randomUUID(),
//                firstSemesterPlanEntity,
//                secondSemesterPlanEntity,
//                thirdSemesterPlanEntity,
//                fourthSemesterPlanEntity,
//                programRepository.findById(educationalProgram.getId()).get()
//        );
//
//        semesterPlanRepository.save(firstSemesterPlanEntity);
//        semesterPlanRepository.save(secondSemesterPlanEntity);
//        semesterPlanRepository.save(thirdSemesterPlanEntity);
//        semesterPlanRepository.save(fourthSemesterPlanEntity);

//        studyPlanRepository.save(syllabusEntity);
    }

    @Override
    public List<StudentSyllabus> findAllByProgram(EducationalProgram educationalProgram) {
        throw new NotImplementedException();
    }

//    @Override
//    public List<Syllabus> findAllByProgram(EducationalProgram educationalProgram) {
//        ProgramEntity programEntity = programRepository.findById(educationalProgram.getId()).get();
//        return studyPlanRepository.findAllByProgram(programEntity)
//                .stream()
//                .map(x -> new Syllabus(
//                        x.getId(),
//                        new SemesterPlan(
//                                x.getFirstSemester().getId(),
//                                new ru.urfu.mm.domain.Semester(
//                                        x.getFirstSemester().getSemester().getId(),
//                                        x.getFirstSemester().getSemester().getYear(),
//                                        SemesterType.values()[x.getFirstSemester().getSemester().getType().ordinal()]
//                                ),
//                                x.getFirstSemester().getRecommendedCredits(),
//                                x.getFirstSemester().getRequiredModules()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList(),
//                                x.getFirstSemester().getSpecialModules()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList(),
//                                x.getFirstSemester().getScienceWork()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList()
//                        ),
//                        new SemesterPlan(
//                                x.getSecondSemester().getId(),
//                                new ru.urfu.mm.domain.Semester(
//                                        x.getSecondSemester().getSemester().getId(),
//                                        x.getSecondSemester().getSemester().getYear(),
//                                        SemesterType.values()[x.getSecondSemester().getSemester().getType().ordinal()]
//                                ),
//                                x.getSecondSemester().getRecommendedCredits(),
//                                x.getSecondSemester().getRequiredModules()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList(),
//                                x.getSecondSemester().getSpecialModules()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList(),
//                                x.getSecondSemester().getScienceWork()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList()
//                        ),
//                        new SemesterPlan(
//                                x.getThirdSemester().getId(),
//                                new ru.urfu.mm.domain.Semester(
//                                        x.getThirdSemester().getSemester().getId(),
//                                        x.getThirdSemester().getSemester().getYear(),
//                                        SemesterType.values()[x.getThirdSemester().getSemester().getType().ordinal()]
//                                ),
//                                x.getThirdSemester().getRecommendedCredits(),
//                                x.getThirdSemester().getRequiredModules()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList(),
//                                x.getThirdSemester().getSpecialModules()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList(),
//                                x.getThirdSemester().getScienceWork()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList()
//                        ),
//                        new SemesterPlan(
//                                x.getFourthSemester().getId(),
//                                new ru.urfu.mm.domain.Semester(
//                                        x.getFourthSemester().getSemester().getId(),
//                                        x.getFourthSemester().getSemester().getYear(),
//                                        SemesterType.values()[x.getFourthSemester().getSemester().getType().ordinal()]
//                                ),
//                                x.getFourthSemester().getRecommendedCredits(),
//                                x.getFourthSemester().getRequiredModules()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList(),
//                                x.getFourthSemester().getSpecialModules()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList(),
//                                x.getFourthSemester().getScienceWork()
//                                        .stream()
//                                        .map(y -> new EducationalModule(
//                                                y.getId(),
//                                                y.getName()
//                                        ))
//                                        .toList()
//                        )
//                ))
//                .toList();
//    }
}
