package ru.urfu.mm.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "educational_programs")
public class EducationalProgram {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private String name;
    @Column
    private String trainingDirection;
    @Column
    private String semesterIdToRequiredCreditsCount;

//    @OneToMany(mappedBy = "id")
//    private List<GroupEntity> groups = new ArrayList<>();

//    public List<GroupEntity> getGroups() {
//        return groups;
//    }

//    public void setGroups(List<GroupEntity> groups) {
//        this.groups = groups;
//    }

    public EducationalProgram() {
    }

    public EducationalProgram(UUID id, String name, String trainingDirection, String semesterIdToRequiredCreditsCount) {
        this.id = id;
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.semesterIdToRequiredCreditsCount = semesterIdToRequiredCreditsCount;
    }

    public EducationalProgram(String name, String trainingDirection, String semesterIdToRequiredCreditsCount) {
        this.name = name;
        this.trainingDirection = trainingDirection;
        this.semesterIdToRequiredCreditsCount = semesterIdToRequiredCreditsCount;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTrainingDirection() {
        return trainingDirection;
    }

    public String getSemesterIdToRequiredCreditsCount() {
        return semesterIdToRequiredCreditsCount;
    }
}
