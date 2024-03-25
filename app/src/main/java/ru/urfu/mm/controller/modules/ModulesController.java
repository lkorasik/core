package ru.urfu.mm.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.applicationlegacy.usecase.CreateModuleWithCourses;
import ru.urfu.mm.applicationlegacy.usecase.DeleteModuleById;
import ru.urfu.mm.applicationlegacy.usecase.GetAllModules;
import ru.urfu.mm.applicationlegacy.usecase.GetModulesByIds;

import java.util.List;

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

    @GetMapping
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

    @PostMapping("/create")
    public void createModule(@RequestBody CreateModuleDTO createModuleDTO) {
        createModuleWithCourses.createModuleWithCourses(createModuleDTO.moduleName(), createModuleDTO.coursesIds());
    }

    @DeleteMapping("/delete")
    public void deleteModule(@RequestBody ModuleIdDTO moduleIdDTO) {
        deleteModuleById.deleteModuleById(moduleIdDTO.educationalModuleId());
    }
}
