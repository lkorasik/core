package ru.urfu.mm.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "educational_modules")
public class Module {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
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
