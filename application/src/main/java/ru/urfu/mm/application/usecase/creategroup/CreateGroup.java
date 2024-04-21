package ru.urfu.mm.application.usecase.creategroup;

import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Program;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Создать академическую группу
 * 1. Проверяем, что указан валидный номер группы. Номер группы должен соответствовать формату МЕНМ-ХХХХХХ.
 * 2. Проверяем, есть ли в системе нужные семестры. Если их нет, то создаем недостающие.
 * 2. Достаем программу и добавляем в нее группу.
 */
public class CreateGroup {
    private final GroupGateway groupGateway;
    private final ProgramGateway programGateway;

    public CreateGroup(GroupGateway groupGateway, ProgramGateway programGateway) {
        this.groupGateway = groupGateway;
        this.programGateway = programGateway;
    }

    public void createGroup(String number, UUID programId) {
        String REGEX = "^\\S\\S\\S\\S-\\d\\d\\d\\d\\d\\d$"; // Провеяем формат МЕНМ-123456
        Pattern regex = Pattern.compile(REGEX);

        if (!regex.asPredicate().test(number)) {
            throw new InvalidGroupNameException(number);
        }

        String[] parts = number.split("-");
        String departmentCode = parts[0];
        int digitalPart = Integer.parseInt(parts[1]);

        ensureDepartmentCode(departmentCode);
        ensureCorrectCourseNumber(digitalPart);

        Group group = new Group(UUID.randomUUID(), number);
        groupGateway.save(group);

        Program program = programGateway.getById(programId);
        var list = new ArrayList<Group>();
        list.addAll(program.getGroups());
        list.add(group);
        program.setGroups(list.stream().toList());
        programGateway.save(program);
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
