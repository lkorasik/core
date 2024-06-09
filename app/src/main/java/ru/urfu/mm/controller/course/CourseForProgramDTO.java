package ru.urfu.mm.controller.course;

import ru.urfu.mm.persistance.entity.SemesterEntity;
import ru.urfu.mm.persistance.entity.enums.Control;

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
        List<SemesterEntity> semesterEntities,
        UUID moduleId,
        UUID requiredSemesterId
) { }

