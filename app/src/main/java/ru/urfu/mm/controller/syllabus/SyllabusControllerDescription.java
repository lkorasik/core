package ru.urfu.mm.controller.syllabus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.controller.program.SyllabusDTO;
import ru.urfu.mm.domain.BaseSyllabus;

import java.util.List;
import java.util.UUID;

@Tag(name = "Syllabus", description = "Управление учебным планом")
@RequestMapping(Endpoints.Syllabus.BASE)
public interface SyllabusControllerDescription {
    @Operation(summary = "Создать базовый учебный план")
    @PostMapping(Endpoints.Syllabus.CREATE)
    void saveSyllabus(@RequestBody SyllabusDTO dto);

    @Operation(summary = "Получить базовый план")
    @PostMapping(Endpoints.Syllabus.PLAN)
    List<BaseSyllabus> getAllSyllabi(@RequestParam("programId") UUID programId);
}
