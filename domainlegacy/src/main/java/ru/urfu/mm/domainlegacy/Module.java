package ru.urfu.mm.domainlegacy;

import java.util.UUID;

public class Module {
    private UUID id;
    private String name;

    public Module(String name) {
        this.name = name;
    }

    public Module() {

    }

    public Module(UUID id, String name) {
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
