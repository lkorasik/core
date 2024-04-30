package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "educational_modules")
public class EducationalModuleEntity {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private String name;
    @OneToMany(mappedBy = "module")
    private List<SpecialCourse> courses;

    public EducationalModuleEntity() {
    }

    public EducationalModuleEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SpecialCourse> getCourses() {
        return courses;
    }
}
