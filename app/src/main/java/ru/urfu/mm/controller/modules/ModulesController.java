package ru.urfu.mm.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.CreateModuleWithCourses;
import ru.urfu.mm.application.usecase.DeleteModuleById;
import ru.urfu.mm.application.usecase.getallmodules.GetAllModules;
import ru.urfu.mm.application.usecase.GetModulesByIds;
import ru.urfu.mm.application.usecase.getmodule.GetModuleWithCourses;
import ru.urfu.mm.application.usecase.getmodule.ModuleWithCoursesResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/modules")
public class ModulesController {
    @Autowired
    private GetAllModules getAllModules;
    @Autowired
    private GetModulesByIds getModulesByIds;
    @Autowired
    private CreateModuleWithCourses createModuleWithCourses;
    @Autowired
    private DeleteModuleById deleteModuleById;
    @Autowired
    private GetModuleWithCourses getModuleWithCourses;

    @GetMapping("/all")
    public List<ModuleDTO> getAllModules() {
        return getAllModules
                .getAllModules()
                .stream()
                .map(x -> new ModuleDTO(x.getId(), x.getName()))
                .toList();
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
        createModuleWithCourses.createModuleWithCourses(createModuleDTO.moduleName(), createModuleDTO.coursesIds());
    }

    @DeleteMapping("/delete")
    public void deleteModule(@RequestBody ModuleIdDTO moduleIdDTO) {
        deleteModuleById.deleteModuleById(moduleIdDTO.id());
    }
}
