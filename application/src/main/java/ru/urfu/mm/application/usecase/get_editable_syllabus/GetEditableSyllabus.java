package ru.urfu.mm.application.usecase.get_editable_syllabus;

import ru.urfu.mm.application.usecase.get_all_modules.GetAllModules;
import ru.urfu.mm.application.usecase.get_base_syllabus.GetAllSyllabi;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.EducationalModule;

import java.util.*;

public class GetEditableSyllabus {
    private final GetAllSyllabi getAllSyllabi;
    private final GetAllModules getAllModules;

    public GetEditableSyllabus(GetAllSyllabi getAllSyllabi, GetAllModules getAllModules) {
        this.getAllSyllabi = getAllSyllabi;
        this.getAllModules = getAllModules;
    }

    public List<GetSyllabusDTO> getEditableSyllabus(UUID programId) {
        List<BaseSyllabus> syllabi = getAllSyllabi.getStudyPlan(programId);

        List<EducationalModule> allModules = getAllModules.getAllModules();

        List<GetSyllabusDTO> getSyllabusDTOS = new ArrayList<>();
        for (BaseSyllabus syllabus : syllabi) {
            List<GetModuleSelectionDTO> modules = new ArrayList<>();

            // Надо сюда ид модуля и список курсов
            List<Course> requiredCourses = syllabus.getFirstSemesterPlan().getRequiredCourses();
            Map<UUID, List<GetCourseSelectionDTO>> courseSelectionDTOMap = new HashMap();
            // модуль -> курс
            for (Course course: requiredCourses) {
                for(EducationalModule module: allModules) {
                    List<UUID> coursesId = module.getCourses()
                            .stream()
                            .map(Course::getId)
                            .toList();

                    if (coursesId.contains(course.getId())) {
                        GetCourseSelectionDTO item = new GetCourseSelectionDTO(
                                course.getId(),
                                syllabus.getFirstSemesterPlan().getSemester().getId()
                        );

                        // Курс course состоит в module
                        if (!courseSelectionDTOMap.containsKey(module.getId())) {
                            List<GetCourseSelectionDTO> courseSelectionDTOS = new ArrayList<>();
                            courseSelectionDTOMap.put(module.getId(), courseSelectionDTOS);
                        }
                        courseSelectionDTOMap.get(module.getId()).add(item);
                    }
                }
            }

            for(UUID moduleId: courseSelectionDTOMap.keySet()) {
                modules.add(new GetModuleSelectionDTO(moduleId, courseSelectionDTOMap.get(moduleId)));
            }

            GetSyllabusDTO dto = new GetSyllabusDTO(
                    programId,
                    syllabus.getFirstSemesterPlan().getSemester().getId(),
                    modules
            );

            getSyllabusDTOS.add(dto);
        }
        return getSyllabusDTOS;
    }
}
