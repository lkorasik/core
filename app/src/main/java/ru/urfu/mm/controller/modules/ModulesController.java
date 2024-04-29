package ru.urfu.mm.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.create_module.CreateModule;
import ru.urfu.mm.application.usecase.DeleteModuleById;
import ru.urfu.mm.application.usecase.get_all_modules.GetAllModules;
import ru.urfu.mm.application.usecase.GetModulesByIds;
import ru.urfu.mm.application.usecase.get_module.GetModuleWithCourses;
import ru.urfu.mm.application.usecase.get_module.ModuleWithCoursesResponse;
import ru.urfu.mm.application.usecase.get_modules_courses.GetModulesCourses;
import ru.urfu.mm.domain.exception.NotImplementedException;

import java.util.*;

@RestController
@RequestMapping("/api/modules")
public class ModulesController {
    @Autowired
    private GetAllModules getAllModules;
    @Autowired
    private GetModulesByIds getModulesByIds;
    @Autowired
    private CreateModule createModule;
    @Autowired
    private DeleteModuleById deleteModuleById;
    @Autowired
    private GetModuleWithCourses getModuleWithCourses;
    @Autowired
    private GetModulesCourses getModulesCourses;

    @GetMapping("/all")
    public List<ModuleDTO> getAllModules() {
        return getAllModules
                .getAllModules()
                .stream()
                .map(x -> new ModuleDTO(x.getId(), x.getName()))
                .toList();
    }

    @GetMapping("/all2")
    public List<FullModuleDTO> getAllModules2() {
        throw new NotImplementedException();
//        List<Course> modulesCourses = getModulesCourses.getModulesCourses();
//
//        Map<UUID, FullModuleDTO> modules = new HashMap<>();
//        for (Course course : modulesCourses) {
//            if (modules.containsKey(course.getEducationalModule().getId())) {
//                FullModuleDTO moduleDTO = modules.get(course.getEducationalModule().getId());
//                FullCourseDTO courseDTO = new FullCourseDTO(course.getId(), course.getName());
//                moduleDTO.courses().add(courseDTO);
//                modules.put(moduleDTO.id(), moduleDTO);
//            } else {
//                FullCourseDTO courseDTO = new FullCourseDTO(course.getId(), course.getName());
//                List<FullCourseDTO> courses = new ArrayList<>();
//                courses.add(courseDTO);
//                FullModuleDTO moduleDTO = new FullModuleDTO(course.getEducationalModule().getId(), course.getEducationalModule().getName(), courses);
//                modules.put(moduleDTO.id(), moduleDTO);
//            }
//        }
//
//        return modules.values().stream().toList();
    }

    @PostMapping
    public List<ModuleDTO> getModulesById(@RequestBody GetModulesDTO getModulesDTO) {
        return getModulesByIds
                .getModulesByIds(getModulesDTO.modulesIds())
                .stream()
                .map(x -> new ModuleDTO(x.getId(), x.getName()))
                .toList();
    }

    @GetMapping("/module")
    public ModuleWithCoursesDTO getModuleById(@RequestParam("id") String moduleId) {
        ModuleWithCoursesResponse module = getModuleWithCourses.getModule(UUID.fromString(moduleId));
        List<CourseDTO> courses = module.courses()
                .stream()
                .map(x -> new CourseDTO(x.id(), x.name()))
                .toList();
        return new ModuleWithCoursesDTO(module.id(), module.name(), courses);
    }

    @PostMapping("/create")
    public void createModule(@RequestBody CreateModuleDTO createModuleDTO) {
        createModule.createModule(createModuleDTO.moduleName());
    }

    @DeleteMapping("/delete")
    public void deleteModule(@RequestBody ModuleIdDTO moduleIdDTO) {
        deleteModuleById.deleteModuleById(moduleIdDTO.id());
    }
}
