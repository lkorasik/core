package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.urfu.mm.persistance.entity.AccountEntity;
import ru.urfu.mm.persistance.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.UUID;

@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AccountEntity accountEntity = accountRepository.findAllByLogin(UUID.fromString(username)).orElseThrow();
        return new User(accountEntity.getLogin().toString(), accountEntity.getPassword(), Collections.emptyList());
    }
}