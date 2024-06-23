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

    @Operation(summary = "Получить список всех модулей с курсами")
    @GetMapping(Endpoints.Module.ALL_WITH_COURSES)
    List<FullModuleDTO> getAllModulesWithCourses();

    @Operation(summary = "Получить информацию о конкретном модуле")
    @GetMapping(Endpoints.Module.MODULE)
    ModuleWithCoursesDTO getModuleById(@RequestParam("id") String moduleId);

    @Operation(summary = "Создать модуль")
    @PostMapping(Endpoints.Module.CREATE)
    void createModule(@RequestBody CreateModuleDTO createModuleDTO);

    @DeleteMapping(Endpoints.Module.DELETE)
    void deleteModule(@RequestBody ModuleIdDTO moduleIdDTO);
}
