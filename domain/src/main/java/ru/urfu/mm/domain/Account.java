package ru.urfu.mm.domain;

import java.util.UUID;

public record Account(UUID login, String password, UserRole role) {
}
