package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.domain.enums.SemesterType;
import ru.urfu.mm.persistance.entity.*;
import ru.urfu.mm.persistance.repository.*;

import java.util.List;
import java.util.UUID;

@Component
public class StudyPlanGatewayImpl implements StudyPlanGateway {
    private final SemesterPlanRepository semesterPlanRepository;
    private final StudyPlanRepository studyPlanRepository;
    private final SemesterRepository semesterRepository;
    private final ProgramRepository programRepository;
    private final SpecialCourseRepository courseRepository;

    @Autowired
    public StudyPlanGatewayImpl(
            SemesterPlanRepository semesterPlanRepository,
            StudyPlanRepository studyPlanRepository,
            SemesterRepository semesterRepository,
            ProgramRepository programRepository,
            SpecialCourseRepository courseRepository) {
        this.semesterPlanRepository = semesterPlanRepository;
        this.studyPlanRepository = studyPlanRepository;
        this.semesterRepository = semesterRepository;
        this.programRepository = programRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void save(Syllabus syllabus, EducationalProgram educationalProgram) {
        SemesterEntity firstSemesterEntity = semesterRepository
                .findById(syllabus.getFirstSemesterPlan().getSemester().getId())
                .get();
        SemesterEntity secondSemesterEntity = semesterRepository
                .findById(syllabus.getSecondSemesterPlan().getSemester().getId())
                .get();
        SemesterEntity thirdSemesterEntity = semesterRepository
                .findById(syllabus.getThirdSemesterPlan().getSemester().getId())
                .get();
        SemesterEntity fourthSemesterEntity = semesterRepository
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

        List<SpecialCourse> firstSemesterCourses = syllabus.getFirstSemesterPlan().getRequiredCourses()
                .stream()
                .map(x -> courseRepository.findById(x.getId()).get())
                .toList();
        List<SpecialCourse> secondSemesterCourses = syllabus.getSecondSemesterPlan().getRequiredCourses()
                .stream()
                .map(x -> courseRepository.findById(x.getId()).get())
                .toList();
        List<SpecialCourse> thirdSemesterCourses = syllabus.getThirdSemesterPlan().getRequiredCourses()
                .stream()
                .map(x -> courseRepository.findById(x.getId()).get())
                .toList();
        List<SpecialCourse> fourthSemesterCourses = syllabus.getFourthSemesterPlan().getRequiredCourses()
                .stream()
                .map(x -> courseRepository.findById(x.getId()).get())
                .toList();

        firstSemesterPlanEntity.getRequiredModules().addAll(firstSemesterCourses);
        secondSemesterPlanEntity.getRequiredModules().addAll(secondSemesterCourses);
        thirdSemesterPlanEntity.getRequiredModules().addAll(thirdSemesterCourses);
        fourthSemesterPlanEntity.getRequiredModules().addAll(fourthSemesterCourses);

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
        EducationalProgramEntity programEntity = programRepository.findById(educationalProgram.getId()).get();
        return studyPlanRepository.findAllByProgram(programEntity)
                .stream()
                .map(x -> new Syllabus(
                        x.getId(),
                        new SemesterPlan(
                                x.getFirstSemester().getId(),
                                new ru.urfu.mm.domain.Semester(
                                        x.getFirstSemester().getSemester().getId(),
                                        x.getFirstSemester().getSemester().getYear(),
                                        SemesterType.values()[x.getFirstSemester().getSemester().getType().ordinal()]
                                ),
                                x.getFirstSemester().getRecommendedCredits()
                        ),
                        new SemesterPlan(
                                x.getSecondSemester().getId(),
                                new ru.urfu.mm.domain.Semester(
                                        x.getSecondSemester().getSemester().getId(),
                                        x.getSecondSemester().getSemester().getYear(),
                                        SemesterType.values()[x.getSecondSemester().getSemester().getType().ordinal()]
                                ),
                                x.getSecondSemester().getRecommendedCredits()
                        ),
                        new SemesterPlan(
                                x.getThirdSemester().getId(),
                                new ru.urfu.mm.domain.Semester(
                                        x.getThirdSemester().getSemester().getId(),
                                        x.getThirdSemester().getSemester().getYear(),
                                        SemesterType.values()[x.getThirdSemester().getSemester().getType().ordinal()]
                                ),
                                x.getThirdSemester().getRecommendedCredits()
                        ),
                        new SemesterPlan(
                                x.getFourthSemester().getId(),
                                new ru.urfu.mm.domain.Semester(
                                        x.getFourthSemester().getSemester().getId(),
                                        x.getFourthSemester().getSemester().getYear(),
                                        SemesterType.values()[x.getFourthSemester().getSemester().getType().ordinal()]
                                ),
                                x.getFourthSemester().getRecommendedCredits()
                        )
                ))
                .toList();
    }
}
