package ru.urfu.mm.domainlegacy;

import java.util.Objects;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Module module = (Module) obj;
        return Objects.equals(id, module.id) && Objects.equals(name, module.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
