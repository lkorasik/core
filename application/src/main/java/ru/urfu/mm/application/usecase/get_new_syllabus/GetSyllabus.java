package ru.urfu.mm.application.usecase.get_new_syllabus;

import ru.urfu.mm.application.gateway.BaseSyllabusPlanGateway;
import ru.urfu.mm.domain.BaseSemesterPlan;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.Course;

import java.util.List;
import java.util.UUID;

public class GetSyllabus {
    private final BaseSyllabusPlanGateway baseSyllabusPlanGateway;

    public GetSyllabus(BaseSyllabusPlanGateway baseSyllabusPlanGateway) {
        this.baseSyllabusPlanGateway = baseSyllabusPlanGateway;
    }

    public GlobalResponse getSyllabus(UUID programId, int startYear) {
        BaseSyllabus syllabus = baseSyllabusPlanGateway.getAllSyllabi(programId)
                .stream()
                .filter(x -> x.getFirstSemesterPlan().getSemester().getYear() == startYear)
                .findFirst()
                .get();

        List<BaseSemesterPlan> plans = List.of(
                syllabus.getFirstSemesterPlan(),
                syllabus.getSecondSemesterPlan(),
                syllabus.getThirdSemesterPlan(),
                syllabus.getFourthSemesterPlan()
        );
        List<Response> list = plans.stream()
                .map(x -> new Response(
                                x.getSemester().getId(),
                                x.getRequiredCourses()
                                        .stream()
                                        .map(Course::getId)
                                        .toList(),
                                x.getAvailableCourses()
                                        .stream()
                                        .map(Course::getId)
                                        .toList(),
                                x.getScienceWorks()
                                        .stream()
                                        .map(Course::getId)
                                        .toList()
                        )
                )
                .toList();

        return new GlobalResponse(
                list.get(0),
                list.get(1),
                list.get(2),
                list.get(3)
        );
    }
}

record Response(UUID semesterId, List<UUID> requiredCourses, List<UUID> availableCourses, List<UUID> scienceWorks) {
}