package ru.urfu.mm.domainlegacy;

import java.util.UUID;

public class Skill {
    private UUID id;
    private String name;

    public Skill() {

    }

    public Skill(String name) {
        this.name = name;
    }

    public Skill(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
