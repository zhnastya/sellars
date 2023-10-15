package com.example.sellars.service.user;

import com.example.sellars.model.Image;
import com.example.sellars.model.User;
import com.example.sellars.model.enums.Role;
import com.example.sellars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    private Image toImageEntity(MultipartFile file) throws IOException {
        Image avatar = new Image();
        avatar.setName(file.getName());
        avatar.setOriginalFileName(file.getOriginalFilename());
        avatar.setContentType(file.getContentType());
        avatar.setSize(file.getSize());
        avatar.setBytes(file.getBytes());
        return avatar;
    }


    @Override
    public boolean createUser(User user) {
        String userEmail = user.getEmail();
        if (userRepository.findByEmail(userEmail) != null) return false;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAvatar(null);
        userRepository.save(user);
        return true;
    }


    @Override
    public boolean updateUser(User user) {
        String userEmail = user.getEmail();
        User user1 = userRepository.findByEmail(userEmail);
        if (user1 == null) return false;
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(Principal principal) {
        User user1 = userRepository.findByEmail(principal.getName());
        if (user1 == null) return false;
        userRepository.deleteById(user1.getId());
        return true;
    }

    @Override
    @SneakyThrows
    public void addAvatar(Principal user, MultipartFile file) {
        String userEmail = user.getName();
        User user1 = userRepository.findByEmail(userEmail);
        if (user1 != null) {
            Image avatar = toImageEntity(file);
            user1.setAvatar(avatar);
            userRepository.save(user1);
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public void blockUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id - {}", id);
            } else {
                user.setActive(true);
                log.info("Reban user with id - {}", id);
            }
        }
    }

    @Override
    public void changeUserRole(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String s : form.keySet()) {
            if (roles.contains(s)) {
                user.getRoles().add(Role.valueOf(s));
            }
        }
        userRepository.save(user);
    }
}
