package ru.urfu.mm.application.usecase.get_base_syllabus;

import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.Course;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Получить базовый учебный план, который был бы удобен для отрисовки в виде таблицы
 */
public class GetBaseSyllabus {
    private final ProgramGateway programGateway;
    private final ModuleGateway moduleGateway;

    public GetBaseSyllabus(ProgramGateway programGateway, ModuleGateway moduleGateway) {
        this.programGateway = programGateway;
        this.moduleGateway = moduleGateway;
    }

    public List<Response> getBaseSyllabus(UUID programId, int startYear) {
        // todo: Сюда надо добавить механизм получения планов по годам
        /*
        *План
        +-модуль
        +--курс
        +---семестр
        */
        List<BaseSyllabus> baseSyllabi = programGateway.findById(programId)
                .get()
                .getAcademicGroups()
                .stream()
                .map(AcademicGroup::getBaseSyllabus)
                .toList();

        Set<UUID> selectedSyllabiId = new HashSet<>();
        List<BaseSyllabus> baseSyllabi1 = new ArrayList<>();
        for(BaseSyllabus syllabus: baseSyllabi) {
            if (selectedSyllabiId.contains(syllabus.getId())) {
                // skip
            } else {
                selectedSyllabiId.add(syllabus.getId());
                baseSyllabi1.add(syllabus);
            }
        }

        return baseSyllabi1
                .stream()
                .map(x -> new Response(
                        x.getId(),
                        x.getFirstSemesterPlan().getSemester().getYear(),
                        getModules(x)
                ))
                .toList();
    }

    private List<ModuleResponse> getModules(BaseSyllabus syllabus) {
        List<CourseResponse> firstCourses = syllabus.getFirstSemesterPlan()
                .getRequiredCourses()
                .stream()
                .map(x -> new CourseResponse(x.getId(), x.getName(), 1))
                .toList();
        List<CourseResponse> secondCourses = syllabus.getSecondSemesterPlan()
                .getRequiredCourses()
                .stream()
                .map(x -> new CourseResponse(x.getId(), x.getName(), 2))
                .toList();
        List<CourseResponse> thirdCourses = syllabus.getThirdSemesterPlan()
                .getRequiredCourses()
                .stream()
                .map(x -> new CourseResponse(x.getId(), x.getName(), 3))
                .toList();
        List<CourseResponse> fourthCourses = syllabus.getFourthSemesterPlan()
                .getRequiredCourses()
                .stream()
                .map(x -> new CourseResponse(x.getId(), x.getName(), 4))
                .toList();

        List<CourseResponse> courses = Stream.of(
                        firstCourses,
                        secondCourses,
                        thirdCourses,
                        fourthCourses
                )
                .flatMap(List::stream)
                .toList();

        return moduleGateway.getAllModules()
                .stream()
                .map(x -> {
                    Set<UUID> coursesId = x.getCourses().stream().map(Course::getId).collect(Collectors.toSet());
                    List<CourseResponse> moduleCourses = courses
                            .stream()
                            .filter(y -> coursesId.contains(y.id()))
                            .toList();
                    return new ModuleResponse(x.getId(), x.getName(), moduleCourses);
                })
                .toList();
    }
}