package ru.urfu.mm.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.core.dto.EducationalProgramInfoDto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EducationalController {
    // todo: немного странный метод. Надо сделать так: сервер просто запрашивает список групп. Только названия. Ничего
    //  лишнего.
    @GetMapping("/educationalPrograms")
    public EducationalProgramInfoDto[] getEducationalProgram() {
        Map<UUID, Integer> map = new HashMap<>();
        map.put(UUID.randomUUID(), 3);
        return new EducationalProgramInfoDto[]{new EducationalProgramInfoDto(UUID.randomUUID(), "Test", map)};
    }
}
