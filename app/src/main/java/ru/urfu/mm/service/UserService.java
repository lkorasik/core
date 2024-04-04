package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.urfu.mm.entity.User;
import ru.urfu.mm.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findAllByLogin(UUID.fromString(username)).orElseThrow();
        return new org.springframework.security.core.userdetails.User(user.getLogin().toString(), user.getPassword(), Collections.emptyList());
    }
}