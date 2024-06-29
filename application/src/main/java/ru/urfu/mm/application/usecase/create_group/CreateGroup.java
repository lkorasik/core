package ru.urfu.mm.application.usecase.create_group;

import ru.urfu.mm.application.gateway.*;
import ru.urfu.mm.application.usecase.create_syylabus.CreateBaseSyllabus;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.domain.enums.SemesterType;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Создать академическую группу
 * 1. Проверяем, что указан валидный номер группы. Номер группы должен соответствовать формату МЕНМ-ХХХХХХ. Если номер
 * группы не совпадает с указанным шаблоном, то кидаем ошибку.
 * 2. Проверяем, есть ли в системе нужные семестры. Если их нет, то создаем недостающие.
 * 3. Создаем базовый семестровые планы
 * 4. Создаем базовый учбеный план
 * 5. Создаем группу
 */
public class CreateGroup {
    private final GroupGateway groupGateway;
    private final ProgramGateway programGateway;
    private final SemesterGateway semesterGateway;
    private final BaseSyllabusPlanGateway baseSyllabusPlanGateway;
    private final BaseSemesterPlanGateway baseSemesterPlanGateway;

    public CreateGroup(
            GroupGateway groupGateway,
            ProgramGateway programGateway,
            SemesterGateway semesterGateway,
            BaseSyllabusPlanGateway baseSyllabusPlanGateway,
            BaseSemesterPlanGateway baseSemesterPlanGateway) {
        this.groupGateway = groupGateway;
        this.programGateway = programGateway;
        this.semesterGateway = semesterGateway;
        this.baseSyllabusPlanGateway = baseSyllabusPlanGateway;
        this.baseSemesterPlanGateway = baseSemesterPlanGateway;
    }

    public void createGroup(CreateGroupRequest request) {
        ensureValidGroupNumber(request.number());

        List<Semester> semesters = ensureActualSemestersExists(request.startYear());

        BaseSemesterPlan firstSemesterPlan = new BaseSemesterPlan(
                UUID.randomUUID(),
                semesters.get(0)
        );
        baseSemesterPlanGateway.save(firstSemesterPlan);

        BaseSemesterPlan secondSemesterPlan = new BaseSemesterPlan(
                UUID.randomUUID(),
                semesters.get(1)
        );
        baseSemesterPlanGateway.save(secondSemesterPlan);

        BaseSemesterPlan thirdSemesterPlan = new BaseSemesterPlan(
                UUID.randomUUID(),
                semesters.get(2)
        );
        baseSemesterPlanGateway.save(thirdSemesterPlan);

        BaseSemesterPlan fourthSemesterPlan = new BaseSemesterPlan(
                UUID.randomUUID(),
                semesters.get(3)
        );
        baseSemesterPlanGateway.save(fourthSemesterPlan);

        BaseSyllabus baseSyllabus = new BaseSyllabus(
                UUID.randomUUID(),
                firstSemesterPlan,
                secondSemesterPlan,
                thirdSemesterPlan,
                fourthSemesterPlan
        );
        baseSyllabusPlanGateway.save(baseSyllabus);

        AcademicGroup academicGroup = new AcademicGroup(
                UUID.randomUUID(),
                request.number(),
                baseSyllabus
        );
        groupGateway.save(academicGroup);

        EducationalProgram program = programGateway.findById(request.programId()).get();
        List<AcademicGroup> groups = new ArrayList<>(program.getAcademicGroups());
        groups.add(academicGroup);
        EducationalProgram newProgram = new EducationalProgram(
                program.getId(),
                program.getName(),
                program.getTrainingDirection(),
                groups
        );
        programGateway.save(newProgram);
    }

    private List<Semester> ensureActualSemestersExists(int startYear) {
        List<Semester> semesters = semesterGateway.getSemestersForEntireStudyPeriod(startYear);
        List<Semester> actualSemesters = List.of(
                ensureSemester(semesters, startYear, SemesterType.FALL),
                ensureSemester(semesters, startYear + 1, SemesterType.SPRING),
                ensureSemester(semesters, startYear + 1, SemesterType.FALL),
                ensureSemester(semesters, startYear + 2, SemesterType.SPRING)
        );
        actualSemesters.forEach(semesterGateway::save);
        return actualSemesters;
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
