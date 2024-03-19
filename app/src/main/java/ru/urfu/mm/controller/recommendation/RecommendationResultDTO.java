package ru.urfu.mm.controller.recommendation;

import java.util.List;

/**
 * Результат подбора курсов
 *
 * @param perfectCourses           Курсы, удовлетворяющие условиям:
 *                                 а) У студента имеются все стартовые навыки
 *                                 б) Курс даёт хотя бы один навык, который интересует студента
 *                                 Внутри группы сортируем по принципу: чем больше нужных студенту навыков даёт курс, тем выше он в выборке.
 * @param partiallySuitableCourses Курсы, удовлетворяющие условиям:
 *                                 а) У студента имеется часть стартовых навыков (хотя бы 1)
 *                                 б) Курс даёт хотя бы один навык, который интересует студента
 *                                 Сортировка в группе сначала по количеству  имеющихся стартовых навыков, потом по количеству интересующих навыков
 * @param complementaryCourses     Курсы, удовлетворяющие условиям:
 *                                 а) Студент удовлетворяет всем стартовым навыкам курса
 *                                 б) Курс даёт навык, который не интересен студенту напрямую, но освоение которого поможет лучше
 *                                 соответствовать стартовым навыкам курсов из <see cref="PartiallySuitableCourses"/>
 *                                 Сортировка в группе сначала по количеству  имеющихся стартовых навыков, потом по количеству интересующих навыков
 * @param moduleCourses            Соответствие между модулем и всеми его курсами.
 *                                 Заполняется только для тех модулей, где есть хотя бы один курс из групп выше:
 *                                 <see cref="PerfectCourses"/>, <see cref="PartiallySuitableCourses"/>, <see cref="ComplementaryCourses"/>
 */
public record RecommendationResultDTO(
        List<RecommendedCourseDTO> perfectCourses,
        List<RecommendedCourseDTO> partiallySuitableCourses,
        List<RecommendedCourseDTO> complementaryCourses,
        List<ModuleCoursesDTO> moduleCourses
) { }
