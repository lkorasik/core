package ru.urfu.mm.core.service;

import org.springframework.stereotype.Service;
import ru.urfu.mm.core.entity.Student;

@Service
public class RecommendationsService {
    public void calculateRecommendations(Student student) {

    }

    /*
    public async Task<RecommendationResultDto> CalculateRecommendations(string studentLogin)
{
    var studentSkills = await studentSkillsRepository.GetSkillsForStudent(studentLogin);
    var studentDesiredSkills = await studentDesiredSkillsRepository.GetSkillsForStudent(studentLogin);

    var student = await studentsRepository.GetByLogin(studentLogin);
    var actualSemestersIds = (await semestersRepository.GetActualSemesters())
        .Select(x => x.Id)
        .ToArray();
    var courses = await coursesBySemestersRepository.GetCoursesByEducationalProgramAndSemesters(
        student.EducationalProgramId,
        actualSemestersIds
    );

    var optionalCourses = courses
        .Where(x => x.RequiredSemesterId is null)
        .ToArray();
    var optionalCoursesIds = optionalCourses.Select(x => x.Id).ToArray();
    var courseIdToRequiredSkills = coursesSkillsRepository.GetCoursesToRequiredSkills(optionalCoursesIds);
    var courseIdToResultSkills = coursesSkillsRepository.GetCoursesToResultSkills(optionalCoursesIds);

    var perfectCoursesIds = GetPerfectCoursesIds(
        courseIdToRequiredSkills,
        courseIdToResultSkills,
        studentSkills,
        studentDesiredSkills
    );
    var partiallySuitableCoursesIds = GetPartiallySuitableCoursesIds(
        courseIdToRequiredSkills,
        courseIdToResultSkills,
        studentSkills,
        studentDesiredSkills
    );
    var complementaryCoursesIds = GetComplementaryCoursesIds(
        courseIdToRequiredSkills,
        courseIdToResultSkills,
        studentSkills,
        partiallySuitableCoursesIds
    );

    var perfectCourses =
        BuildCoursesWithSkills(perfectCoursesIds, courseIdToRequiredSkills, courseIdToResultSkills)
            .OrderByDescending(x =>
            {
                var desiredSkillsIds = studentDesiredSkills.Select(x => x.Id);
                var courseResultSkillsIds = x.ResultSkills.Select(y => y.Id);
                return courseResultSkillsIds.Intersect(desiredSkillsIds).Count();
            });
    var partiallySuitableCourses =
        BuildCoursesWithSkills(partiallySuitableCoursesIds, courseIdToRequiredSkills, courseIdToResultSkills)
        .OrderByDescending(x =>
        {
            var studentActualSkillsIds = studentSkills.Select(x => x.Id);
            var desiredSkillsIds = studentDesiredSkills.Select(x => x.Id);
            var courseRequiredSkillsIds = x.RequiredSkills.Select(x => x.Id);
            var courseResultSkillsIds = x.ResultSkills.Select(y => y.Id);

            // Двухступенчатая сортировка. Сначала по требуемым курсам, потом по желаемым для изучения
            return courseRequiredSkillsIds.Intersect(studentActualSkillsIds).Count() * 1000 +
                   courseResultSkillsIds.Intersect(desiredSkillsIds).Count();
        });
    var complementaryCourses =
        BuildCoursesWithSkills(complementaryCoursesIds, courseIdToRequiredSkills, courseIdToResultSkills)
            .OrderByDescending(x =>
            {
                var skillsToLearn = GetSkillsToLearn(
                    partiallySuitableCourses.SelectMany(y => y.RequiredSkills).ToArray(),
                    studentSkills
                );
                var skillsToLearnIds = skillsToLearn.Select(y => y.Id);
                var courseResultSkillsIds = x.ResultSkills.Select(y => y.Id);
                return skillsToLearnIds.Intersect(courseResultSkillsIds).Count();
            });

    var coursesById = courses.ToDictionary(x => x.Id);
    var coursesByModule = courses
        .Where(x => x.EducationalModuleId != null)
        .GroupBy(x => x.EducationalModuleId.Value);

    return new RecommendationResultDto
    {
        PerfectCourses = perfectCourses.Select(x => BuildRecommendedCourse(coursesById, x)).ToArray(),
        PartiallySuitableCourses = partiallySuitableCourses.Select(x => BuildRecommendedCourse(coursesById, x))
            .ToArray(),
        ComplementaryCourses =
            complementaryCourses.Select(x => BuildRecommendedCourse(coursesById, x)).ToArray(),
        ModuleCourses = coursesByModule.Select(x => new ModuleCoursesDto()
            {
                ModuleId = x.Key,
                Courses = x
                    .Select(y => new RecommendedCourse()
                    {
                        Id = y.Id,
                        Name = y.Name,
                        CreditsCount = y.CreditsCount,
                        Control = y.Control,
                        Description = y.Description,
                        Semesters = y.Semesters,
                        EducationalModuleId = y.EducationalModuleId,
                        RequiredSemesterId = y.RequiredSemesterId,
                        RequiredSkills = courseIdToRequiredSkills.TryGetValue(y.Id, out var requiredSkills)
                            ? requiredSkills
                            : Array.Empty<Skill>(),
                        ResultSkills = courseIdToResultSkills.TryGetValue(y.Id, out var resultSkills)
                            ? resultSkills
                            : Array.Empty<Skill>(),
                    })
                    .ToArray()
            })
            .ToArray()
    };
}
     */
}
