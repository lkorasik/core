package ru.urfu.mm.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.service.ModulesService;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModulesController {
    @Autowired
    private ModulesService modulesService;

    @GetMapping
    public List<ModuleDTO> getAllModules() {
        return modulesService
                .getAllModules()
                .stream()
                .map(x -> new ModuleDTO(x.getId(), x.getName()))
                .toList();
    }

    @PostMapping
    public List<ModuleDTO> getModulesById(@RequestBody GetModulesDTO getModulesDTO) {
        return modulesService
                .getModulesByIds(getModulesDTO.modulesIds())
                .stream()
                .map(x -> new ModuleDTO(x.getId(), x.getName()))
                .toList();
    }

    @PostMapping("/create")
    public void createModule(@RequestBody CreateModuleDTO createModuleDTO) {
        modulesService.createModuleWithCourses(
                createModuleDTO.moduleName(),
                createModuleDTO.coursesIds());
    }

    @DeleteMapping("/delete")
    public void deleteModule(@RequestBody ModuleIdDTO moduleIdDTO) {
        modulesService.deleteModuleById(moduleIdDTO.educationalModuleId());
    }
}
