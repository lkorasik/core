package ru.urfu.mm.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @Column
    private UUID id;
    @Column
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
