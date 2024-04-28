package ru.urfu.mm.dsl;

import ru.urfu.mm.persistance.entity.ProgramEntity;

import java.util.UUID;

/**
 * Описание DSL для манипуляций с сущностями из базы данных (бизнес объекты)
 */
public class EntityDSL {
    public static ProgramEntity createEducationalProgram() {
        UUID educationalProgramId = UUID.randomUUID();
        String name = UUID.randomUUID().toString();
        String trainingDirection = UUID.randomUUID().toString();
        return new ProgramEntity(educationalProgramId, name, trainingDirection, "{}");
    }
}
