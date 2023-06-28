package com.example.school_service.service;

import com.example.school_service.entity.Role;
import com.example.school_service.entity.User;
import com.example.school_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User addUser(User user){
        User newUser = User.builder().username(user.getUsername()).password(passwordEncoder.encode(user.getPassword())).email(user.getEmail())
                .role(Role.valueOf(user.getRole().name())).build();
        userRepository.save(newUser);
        return newUser;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
