package ru.urfu.mm.controller.modules;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.controller.Endpoints;

import java.util.List;

@Tag(name = "Module", description = "Управление модулями")
public interface ModulesControllerDescription {
    @Operation(summary = "Получить список всех модулей")
    @GetMapping(Endpoints.Module.ALL)
    List<ModuleDTO> getAllModules();

    @GetMapping(Endpoints.Module.ALL2)
    List<FullModuleDTO> getAllModules2();

    @PostMapping
    List<ModuleDTO> getModulesById(@RequestBody GetModulesDTO getModulesDTO);

    @Operation(summary = "Получить информацию о конкретном модуле")
    @GetMapping(Endpoints.Module.MODULE)
    ModuleWithCoursesDTO getModuleById(@RequestParam("id") String moduleId);

    @Operation(summary = "Создать модуль")
    @PostMapping(Endpoints.Module.CREATE)
    void createModule(@RequestBody CreateModuleDTO createModuleDTO);

    @DeleteMapping(Endpoints.Module.DELETE)
    void deleteModule(@RequestBody ModuleIdDTO moduleIdDTO);
}
