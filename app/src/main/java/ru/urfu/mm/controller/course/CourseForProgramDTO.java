package ru.urfu.mm.controller.course;

import ru.urfu.mm.entity.Control;
import ru.urfu.mm.entity.Semester;

import java.util.List;
import java.util.UUID;

/**
 * @param requiredSemesterId Если курс является обязательным, то поле заполняется programId семестра, в котором этот
 *                           курс будет обязательным, иначе - null.
 */
public record CourseForProgramDTO(
        UUID id,
        String name,
        int creditsCount,
        Control control,
        String description,
        List<Semester> semesters,
        UUID moduleId,
        UUID requiredSemesterId
) { }

