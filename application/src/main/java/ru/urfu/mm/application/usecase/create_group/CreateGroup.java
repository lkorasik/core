package ru.urfu.mm.application.usecase.create_group;

import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.application.usecase.create_study_plan.CreateStudyPlan;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.domain.enums.SemesterType;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Создать академическую группу
 * 1. Проверяем, что указан валидный номер группы. Номер группы должен соответствовать формату МЕНМ-ХХХХХХ. Если номер
 * группы не совпадает с указанным шаблоном, то кидаем ошибку.
 * 2. Проверяем, есть ли в системе нужные семестры. Если их нет, то создаем недостающие.
 * 3. Если учебного плана еще нет, то создаем его.
 * 4. Достаем программу и добавляем в нее группу.
 */
public class CreateGroup {
    private final GroupGateway groupGateway;
    private final ProgramGateway programGateway;
    private final SemesterGateway semesterGateway;
    private final CreateStudyPlan createStudyPlan;

    public CreateGroup(
            GroupGateway groupGateway,
            ProgramGateway programGateway,
            SemesterGateway semesterGateway,
            CreateStudyPlan createStudyPlan) {
        this.groupGateway = groupGateway;
        this.programGateway = programGateway;
        this.semesterGateway = semesterGateway;
        this.createStudyPlan = createStudyPlan;
    }

    public void createGroup(CreateGroupRequest request) {
        ensureValidGroupNumber(request.number());

        ensureActualSemestersExists(request.startYear());

        Group group = new Group(UUID.randomUUID(), request.number());
        groupGateway.save(group);

        createStudyPlan.createStudyPlan(request.startYear(), request.programId());

        EducationalProgram educationalProgram = programGateway.getById(request.programId());
        var list = new ArrayList<Group>();
        list.addAll(educationalProgram.getGroups());
        list.add(group);
        educationalProgram.setGroups(list.stream().toList());
        programGateway.save(educationalProgram);
    }

    private void ensureActualSemestersExists(int startYear) {
        List<Semester> semesters = semesterGateway.getSemestersForEntireStudyPeriod(startYear);
        List<Semester> actualSemesters = List.of(
                ensureSemester(semesters, startYear, SemesterType.FALL),
                ensureSemester(semesters, startYear + 1, SemesterType.SPRING),
                ensureSemester(semesters, startYear + 1, SemesterType.FALL),
                ensureSemester(semesters, startYear + 2, SemesterType.SPRING)
        );
        actualSemesters.forEach(semesterGateway::save);
    }

    private Semester ensureSemester(List<Semester> semesters, int startYear, SemesterType semesterType) {
        return semesters
                .stream()
                .filter(x -> (x.getYear() == startYear) && (x.getType() == semesterType))
                .findFirst()
                .orElseGet(() -> new Semester(UUID.randomUUID(), startYear, semesterType));
    }

    private String extractDepartmentName(String number) {
        return number.split("-")[0];
    }

    private int extractDigit(String number) {
        return Integer.parseInt(number.split("-")[1]);
    }

    private void ensureValidGroupNumber(String number) {
        String REGEX = "^\\S\\S\\S\\S-\\d\\d\\d\\d\\d\\d$"; // Провеяем формат МЕНМ-123456
        Pattern regex = Pattern.compile(REGEX);

        if (!regex.asPredicate().test(number)) {
            throw new InvalidGroupNameException(number);
        }

        ensureDepartmentCode(extractDepartmentName(number));
        ensureCorrectCourseNumber(extractDigit(number));
    }

    private void ensureDepartmentCode(String department) {
        if (!department.equals("МЕНМ")) {
            throw new InvalidDepartmentException(department);
        }
    }

    private void ensureCorrectCourseNumber(int number) {
        int firstDigitMask = 100000;
        int digit = number / firstDigitMask;
        if ((digit != 1) && (digit != 2)) {
            throw new InvalidCourseNumberException(digit);
        }
    }
}
