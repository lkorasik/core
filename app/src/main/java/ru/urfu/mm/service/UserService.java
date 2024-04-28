package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.urfu.mm.entity.AccountEntity;
import ru.urfu.mm.repository.AccountRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public UserService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

//    @Transactional
    public UserDetails loadUserByUsername(String username) {
        AccountEntity accountEntity = accountRepository.findAllByLogin(UUID.fromString(username)).orElseThrow();
        return new org.springframework.security.core.userdetails.User(accountEntity.getLogin().toString(), accountEntity.getPassword(), Collections.emptyList());
    }
}