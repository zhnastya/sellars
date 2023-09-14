package com.example.sellars.service;

import com.example.sellars.models.User;
import com.example.sellars.models.enums.Role;
import com.example.sellars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public boolean createUser(User user){
        String email = user.getEmail();
        if (repository.findByEmail(email)!=null) return false;
        user.setActive(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        log.info("Saving new User by email - {}", email);
        repository.save(user);
        return true;
    }

}
