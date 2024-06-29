package ru.urfu.mm.controller.program;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.controller.Endpoints;

import java.util.List;
import java.util.UUID;

@Tag(name = "Program", description = "Управление программами")
@RequestMapping(Endpoints.Program.BASE)
public interface ProgramControllerDescription {
    @Operation(summary = "Получить программу по идентификатору")
    @GetMapping(Endpoints.Program.PROGRAM)
    FullProgramDTO getEducationalProgram(@RequestParam("id") UUID programId);

    @Operation(summary = "Создать программу")
    @PostMapping(Endpoints.Program.CREATE)
    void createEducationalProgram(@RequestBody CreateProgramDTO dto);

    @Operation(summary = "Получить все программы")
    @GetMapping(Endpoints.Program.ALL)
    List<ShortProgramDTO> getAll();
}
