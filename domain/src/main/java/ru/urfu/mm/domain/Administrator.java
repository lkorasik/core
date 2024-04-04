package ru.urfu.mm.domain;

/**
 * Администатор.
 * Представляет собой администратора нашей системы
 */
public class Administrator extends User {
    public Administrator(String token, String password) {
        super(token, password);
    }
}
