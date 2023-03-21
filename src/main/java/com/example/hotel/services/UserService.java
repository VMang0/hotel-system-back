package com.example.hotel.services;

import com.example.hotel.DTO.LoginDTO;
import com.example.hotel.DTO.UserDTO;
import com.example.hotel.models.User;
import com.example.hotel.models.enums.Role;
import com.example.hotel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createNewUser(User user) {
        boolean check = true;
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return null;
        user.getRoles().add(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    /*public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user;
        *//*if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;*//*
    }*/

    public User authenticate(LoginDTO loginDTO) {
        User user = null;
        try {
            user = userRepository.findByEmail(loginDTO.getEmail());
        }catch (Exception e) {
            e.printStackTrace();
        }
        String rawPassword = loginDTO.getPassword();
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }

}






