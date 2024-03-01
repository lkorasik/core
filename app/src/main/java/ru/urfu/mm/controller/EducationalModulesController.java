package ru.urfu.mm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.dto.CreateModuleDTO;
import ru.urfu.mm.dto.GetModulesDTO;
import ru.urfu.mm.dto.ModuleIdDTO;
import ru.urfu.mm.entity.EducationalModule;
import ru.urfu.mm.service.EducationalModulesService;

import java.util.List;

@RestController
@RequestMapping("/api/educationalModules")
public class EducationalModulesController {
    @Autowired
    private EducationalModulesService educationalModulesService;

    @GetMapping
    public List<EducationalModule> getAllModules() {
        return educationalModulesService.getAllModules();
    }

    @PostMapping
    public List<EducationalModule> getModulesById(@RequestBody GetModulesDTO getModulesDTO) {
        return educationalModulesService.getModulesByIds(getModulesDTO.getModulesIds());
    }

    @PostMapping("/create")
    public void createModule(@RequestBody CreateModuleDTO createModuleDTO) {
        educationalModulesService.createModuleWithCourses(
                createModuleDTO.getEducationalModuleName(),
                createModuleDTO.getSpecialCoursesIds());
    }

    @DeleteMapping("/delete")
    public void deleteModule(@RequestBody ModuleIdDTO moduleIdDTO) {
        educationalModulesService.deleteModuleById(moduleIdDTO.getEducationalModuleId());
    }
}
