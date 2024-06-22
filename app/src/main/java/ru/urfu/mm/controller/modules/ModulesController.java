package ru.urfu.mm.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.usecase.create_module.CreateModule;
import ru.urfu.mm.application.usecase.DeleteModuleById;
import ru.urfu.mm.application.usecase.get_all_modules.GetAllModules;
import ru.urfu.mm.application.usecase.GetModulesByIds;
import ru.urfu.mm.application.usecase.get_module.GetModuleWithCourses;
import ru.urfu.mm.application.usecase.get_module.ModuleWithCoursesResponse;
import ru.urfu.mm.application.usecase.get_modules_courses.GetModulesCourses;
import ru.urfu.mm.controller.Endpoints;

import java.util.*;

@RestController
@RequestMapping(Endpoints.Module.BASE)
public class ModulesController implements ModulesControllerDescription {
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

    @Override
    public List<ModuleDTO> getAllModules() {
        return getAllModules
                .getAllModules()
                .stream()
                .map(x -> new ModuleDTO(x.getId(), x.getName()))
                .toList();
    }

    @Override
    public List<FullModuleDTO> getAllModulesWithCourses() {
        return getAllModules.getAllModules()
                .stream()
                .map(module -> {
                    List<FullCourseDTO> courses = module.getCourses()
                            .stream()
                            .map(course -> new FullCourseDTO(course.getId(), course.getName()))
                            .toList();
                    return new FullModuleDTO(module.getId(), module.getName(), courses);
                })
                .toList();
    }

    @Override
    public List<ModuleDTO> getModulesById(@RequestBody GetModulesDTO getModulesDTO) {
        throw new NotImplementedException();
//        return getModulesByIds
//                .getModulesByIds(getModulesDTO.modulesIds())
//                .stream()
//                .map(x -> new ModuleDTO(x.getId(), x.getName()))
//                .toList();
    }

    @Override
    public ModuleWithCoursesDTO getModuleById(@RequestParam("id") String moduleId) {
        ModuleWithCoursesResponse module = getModuleWithCourses.getModule(UUID.fromString(moduleId));
        List<CourseDTO> courses = module.courses()
                .stream()
                .map(x -> new CourseDTO(x.id(), x.name()))
                .toList();
        return new ModuleWithCoursesDTO(module.id(), module.name(), courses);
    }

    @Override
    public void createModule(@RequestBody CreateModuleDTO createModuleDTO) {
        createModule.createModule(createModuleDTO.moduleName());
    }

    @Override
    public void deleteModule(@RequestBody ModuleIdDTO moduleIdDTO) {
        deleteModuleById.deleteModuleById(moduleIdDTO.id());
    }
}
