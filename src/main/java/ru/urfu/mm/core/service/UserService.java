package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.dto.RegistrationDTO;
import ru.urfu.mm.core.entity.User;
import ru.urfu.mm.core.exceptions.UsernameAlreadyExistsException;
import ru.urfu.mm.core.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public long createUser(RegistrationDTO dto) {
        if (repository.findAllByUsername(dto.getName()).isPresent()) {
            throw new UsernameAlreadyExistsException(dto.getName());
        }
        User user = new User(0L, dto.getName(), passwordEncoder.encode(dto.getPassword()));
        repository.save(user);
        return user.id;
    }


//    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findAllByUsername(username).orElseThrow();
        return new org.springframework.security.core.userdetails.User(user.username, user.password, Collections.emptyList());
    }
}