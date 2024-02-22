package ru.urfu.mm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.core.dto.GetModulesDTO;
import ru.urfu.mm.core.entity.EducationalModule;
import ru.urfu.mm.core.service.EducationalModulesService;

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
}
