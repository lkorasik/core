package ru.urfu.mm.controller.recommendation;

import java.util.List;
import java.util.UUID;

/**
 * @param requiredSemesterId Если курс является обязательным, то поле заполняется id семестра,
 *                           в котором этот курс будет обязательным, иначе - null.
 */
public record RecommendedCourseDTO(
        UUID id,
        String name,
        int credits,
        List<SemesterDTO> semesters,
        UUID moduleId,
        UUID requiredSemesterId,
        List<SkillDTO> requiredSkills,
        List<SkillDTO> resultSkills
) { }
