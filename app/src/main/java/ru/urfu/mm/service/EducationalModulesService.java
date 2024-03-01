package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.entity.EducationalModule;
import ru.urfu.mm.repository.EducationalModuleRepository;
import ru.urfu.mm.repository.SpecialCourseRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EducationalModulesService {
    @Autowired
    private EducationalModuleRepository educationalModuleRepository;
    @Autowired
    private SpecialCourseRepository specialCourseRepository;

    public List<EducationalModule> getAllModules() {
        return educationalModuleRepository.findAll();
    }

    public List<EducationalModule> getModulesByIds(List<UUID> educationalModulesIds) {
        return educationalModuleRepository
                .findAll()
                .stream()
                .filter(x -> educationalModulesIds.contains(x.getId()))
                .map(x -> new EducationalModule(x.getId(), x.getName()))
                .toList();
    }

    public void createModuleWithCourses(String educationalModuleName, List<UUID> specialCoursesIds) {
        var educationalModuleEntity = educationalModuleRepository
                .save(new EducationalModule(educationalModuleName));
        for(var specialCourseId : specialCoursesIds) {
            var specialCourseModel = specialCourseRepository
                    .findAll()
                    .stream()
                    .filter(x -> x.getId().equals(specialCourseId))
                    .findFirst();

            specialCourseModel.get().setEducationalModule(educationalModuleEntity);
        }
    }

    public void deleteModuleById(UUID educationalModuleId) {
        var module = educationalModuleRepository
                .findById(educationalModuleId)
                .get();

        var moduleCourses = specialCourseRepository
                .findAll()
                .stream()
                .filter(x -> educationalModuleId.equals(x.getEducationalModule().getId()))
                .toList();

        for(var specialCourse : moduleCourses) {
            specialCourse.setEducationalModule(null);
        }
        educationalModuleRepository.delete(module);
    }
}
