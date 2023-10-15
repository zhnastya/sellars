package com.example.sellars.service.user;

import com.example.sellars.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface UserService {

    boolean createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Principal principal);

    void addAvatar(Principal user, MultipartFile file);

    List<User> getUsers();

    User getUserByEmail(String email);
    User getUserById(Long id);

    void blockUser(Long id);

    void changeUserRole(User user, Map<String, String> form);
}
