package ru.urfu.mm.application.usecase.creategroup;

import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.domain.Group;

import java.util.regex.Pattern;

/**
 * Создать академическую группу
 * 1. Проверяем, что указан валидный номер группы. Номер группы должен соответствовать формату МЕНМ-ХХХХХХ.
 */
public class CreateGroup {
    /**
     * МЕНМ-123456
     */
    private final String REGEX = "^\\S\\S\\S\\S-\\d\\d\\d\\d\\d\\d$";
    private final int firstDigitMask = 100000;

    private final GroupGateway groupGateway;

    public CreateGroup(GroupGateway groupGateway) {
        this.groupGateway = groupGateway;
    }

    public void createGroup(String number) {
        Pattern regex = Pattern.compile(REGEX);

        if (!regex.asPredicate().test(number)) {
            throw new InvalidGroupNameException(number);
        }

        String[] parts = number.split("-");
        String departmentCode = parts[0];
        int digitalPart = Integer.parseInt(parts[1]);

        ensureDepartmentCode(departmentCode);
        ensureCorrectCourseNumber(digitalPart);

        Group group = new Group(number);
        groupGateway.save(group);
    }

    private void ensureDepartmentCode(String department) {
        if (!department.equals("МЕНМ")) {
            throw new InvalidDepartmentException(department);
        }
    }

    private void ensureCorrectCourseNumber(int number) {
        int digit = number / firstDigitMask;
        if ((digit != 1) && (digit != 2)) {
            throw new InvalidCourseNumberException(digit);
        }
    }
}
