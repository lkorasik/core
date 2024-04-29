package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.UserRole;

import java.util.UUID;

public record Account(UUID login, String password, UserRole role) {
}
