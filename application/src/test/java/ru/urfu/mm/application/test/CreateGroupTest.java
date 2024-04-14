package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.application.usecase.creategroup.CreateGroup;
import ru.urfu.mm.application.usecase.creategroup.InvalidCourseNumberException;
import ru.urfu.mm.application.usecase.creategroup.InvalidDepartmentException;
import ru.urfu.mm.application.usecase.creategroup.InvalidGroupNameException;

@ExtendWith(MockitoExtension.class)
public class CreateGroupTest {
    @Mock
    private GroupGateway groupGateway;

    /**
     * Создание группы
     */
    @Test
    public void createGroup() {
        String number = "МЕНМ-213123";

        CreateGroup createGroup = new CreateGroup(groupGateway);

        createGroup.createGroup(number);
    }

    /**
     * Создание группы. Некорректный номер.
     */
    @Test
    public void createGroup_invalidRegex() {
        String number = "МЕНМ-654";

        CreateGroup createGroup = new CreateGroup(groupGateway);

        Assertions.assertThrows(InvalidGroupNameException.class, () -> createGroup.createGroup(number));
    }

    /**
     * Создание группы. Некорректный код инстиута.
     */
    @Test
    public void createGroup_invalidDepartmentCode() {
        String number = "АБВГ-213123";

        CreateGroup createGroup = new CreateGroup(groupGateway);

        Assertions.assertThrows(InvalidDepartmentException.class, () -> createGroup.createGroup(number));
    }

    /**
     * Создание группы. Номер курса меньше, чем должен быть.
     */
    @Test
    public void createGroup_courseNumberLess() {
        String number = "МЕНМ-013123";

        CreateGroup createGroup = new CreateGroup(groupGateway);

        Assertions.assertThrows(InvalidCourseNumberException.class, () -> createGroup.createGroup(number));
    }

    /**
     * Создание группы. Номер курса больше, чем должен быть.
     */
    @Test
    public void createGroup_courseNumberGreat() {
        String number = "МЕНМ-313123";

        CreateGroup createGroup = new CreateGroup(groupGateway);

        Assertions.assertThrows(InvalidCourseNumberException.class, () -> createGroup.createGroup(number));
    }
}
