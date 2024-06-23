package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @Column
    private UUID id;
    @OneToOne
    @JoinColumn(name = "account_login")
    private AccountEntity account;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    public StudentEntity() {
    }

    public StudentEntity(UUID id) {
        this.id = id;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public UUID getId() {
        return id;
    }

    public AccountEntity getUser() {
        return account;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }
}
