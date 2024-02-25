package ru.urfu.mm.core.dto;

import java.util.List;

/**
 * Результат подбора курсов
 */
public class RecommendationResultDTO {
    /**
     * Курсы, удовлетворяющие условиям:
     *      а) У студента имеются все стартовые навыки
     *      б) Курс даёт хотя бы один навык, который интересует студента
     * Внутри группы сортируем по принципу: чем больше нужных студенту навыков даёт курс, тем выше он в выборке.
     */
    private List<RecommendedCourseDTO> perfectCourses;
    /**
     * Курсы, удовлетворяющие условиям:
     *     а) У студента имеется часть стартовых навыков (хотя бы 1)
     *     б) Курс даёт хотя бы один навык, который интересует студента
     * Сортировка в группе сначала по количеству  имеющихся стартовых навыков, потом по количеству интересующих навыков
     */
    private List<RecommendedCourseDTO> partiallySuitableCourses;
    /**
     * Курсы, удовлетворяющие условиям:
     *      а) Студент удовлетворяет всем стартовым навыкам курса
     *      б) Курс даёт навык, который не интересен студенту напрямую, но освоение которого поможет лучше
     *          соответствовать стартовым навыкам курсов из <see cref="PartiallySuitableCourses"/>
     * Сортировка в группе сначала по количеству  имеющихся стартовых навыков, потом по количеству интересующих навыков
     */
    private List<RecommendedCourseDTO> complementaryCourses;
    /**
     * Соответствие между модулем и всеми его курсами.
     * Заполняется только для тех модулей, где есть хотя бы один курс из групп выше:
     * <see cref="PerfectCourses"/>, <see cref="PartiallySuitableCourses"/>, <see cref="ComplementaryCourses"/>
     */
    private List<ModuleCoursesDTO> moduleCourses;

    public RecommendationResultDTO() {

    }

    public RecommendationResultDTO(List<RecommendedCourseDTO> perfectCourses, List<RecommendedCourseDTO> partiallySuitableCourses, List<RecommendedCourseDTO> complementaryCourses, List<ModuleCoursesDTO> moduleCourses) {
        this.perfectCourses = perfectCourses;
        this.partiallySuitableCourses = partiallySuitableCourses;
        this.complementaryCourses = complementaryCourses;
        this.moduleCourses = moduleCourses;
    }

    public List<RecommendedCourseDTO> getPerfectCourses() {
        return perfectCourses;
    }

    public List<RecommendedCourseDTO> getPartiallySuitableCourses() {
        return partiallySuitableCourses;
    }

    public List<RecommendedCourseDTO> getComplementaryCourses() {
        return complementaryCourses;
    }

    public List<ModuleCoursesDTO> getModuleCourses() {
        return moduleCourses;
    }
}
