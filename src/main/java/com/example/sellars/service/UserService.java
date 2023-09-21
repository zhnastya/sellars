package com.example.sellars.service;

import com.example.sellars.models.Image;
import com.example.sellars.models.User;
import com.example.sellars.models.enums.Role;
import com.example.sellars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String userEmail = user.getEmail();
        if (userRepository.findByEmail(userEmail) != null) return false;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAvatar(null);
        log.info("Saving new User with email: {}", userEmail);
        userRepository.save(user);
        return true;
    }

    public void addAvatar(Principal user, MultipartFile file) throws IOException {
        String userEmail = user.getName();
        User user1 = userRepository.findByEmail(userEmail);
        if (user1!= null) {
            userRepository.delete(user1);
            Image avatar = toImageEntity(file);
            user1.setAvatar(avatar);
            userRepository.save(user1);
        }
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image avatar = new Image();
        avatar.setName(file.getName());
        avatar.setOriginalFileName(file.getOriginalFilename());
        avatar.setContentType(file.getContentType());
        avatar.setSize(file.getSize());
        avatar.setBytes(file.getBytes());
        return avatar;
    }

    public List<User> getUsers(){
       return userRepository.findAll();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void blockUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if (user!=null){
            if (user.isActive()){
                user.setActive(false);
                log.info("Ban user with id - {}", id);
            }else {
                user.setActive(true);
                log.info("Reban user with id - {}", id);
            }
            userRepository.save(user);
        }
    }

    public void changeUserRole(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String s : form.keySet()) {
            if (roles.contains(s)){
                user.getRoles().add(Role.valueOf(s));
            }
        }
        userRepository.save(user);
    }
}
