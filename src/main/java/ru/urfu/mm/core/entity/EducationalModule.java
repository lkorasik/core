package ru.urfu.mm.core.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "educational_modules")
public class EducationalModule {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private String name;

    public EducationalModule(String name) {
        this.name = name;
    }

    public EducationalModule() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
