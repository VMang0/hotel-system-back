package com.example.hotel.services;

import com.example.hotel.DTO.LoginDTO;
import com.example.hotel.models.User;
import com.example.hotel.models.enums.Role;
import com.example.hotel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createNewUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists");
        }

        user.getRoles().add(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public User createNewManager(User user){
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists");
        }
        user.getRoles().add(Role.MANAGER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
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

    public List<User> listUser(){
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(Role.USER))
                .collect(Collectors.toList());
    }

    public List<User> listManager(){
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(Role.MANAGER))
                .collect(Collectors.toList());
    }

}






