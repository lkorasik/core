package ru.urfu.mm.application.usecase.get_base_syllabus;

import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.Course;

import java.util.List;
import java.util.Set;
import java.util.UUID;
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

    public List<ModuleResponse> getBaseSyllabus(UUID programId, int startYear) {
        // todo: Сюда надо добавить механизм получения планов по годам
        /*
        *План
        +-модуль
        +--курс
        +---семестр
        */
        BaseSyllabus syllabus = programGateway.findById(programId)
                .get()
                .getAcademicGroups()
                .stream()
                .map(AcademicGroup::getBaseSyllabus)
                .filter(x -> x.getFirstSemesterPlan().getSemester().getYear() == startYear)
                .findFirst()
                .get();

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