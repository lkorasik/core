package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @Column
    private UUID login;
    @ManyToOne
    @JoinColumn(name = "educational_program_id")
    private ProgramEntity programEntity;
    @OneToOne
    @JoinColumn(name = "users_login")
    private AccountEntity accountEntity;
    @ManyToOne
    @JoinColumn(name = "groups_id")
    private GroupEntity group;

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public ProgramEntity getEducationalProgram() {
        return programEntity;
    }

    public void setEducationalProgram(ProgramEntity programEntity) {
        this.programEntity = programEntity;
    }

    public StudentEntity() {
    }

    public StudentEntity(UUID login, ProgramEntity programEntity, GroupEntity group, AccountEntity accountEntity) {
        this.login = login;
        this.programEntity = programEntity;
        this.group = group;
        this.accountEntity = accountEntity;
    }

    public StudentEntity(UUID login, ProgramEntity programEntity, GroupEntity group) {
        this.login = login;
        this.programEntity = programEntity;
        this.group = group;
    }

    public UUID getLogin() {
        return login;
    }

    public AccountEntity getUser() {
        return accountEntity;
    }
}
