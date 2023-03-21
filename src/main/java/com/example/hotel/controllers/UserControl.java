package com.example.hotel.controllers;

import com.example.hotel.DTO.LoginDTO;
import com.example.hotel.configuratoins.NotFoundException;
import com.example.hotel.models.User;
import com.example.hotel.repositories.UserRepository;
import com.example.hotel.services.CustomUserDetailsService;
import com.example.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class UserControl {
    @Autowired
    private  UserService userService;
    @Autowired
    private UserRepository userRepository;
    /*@Autowired
    private AuthenticationManager authenticationManager;*/
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private CustomUserDetailsService customUserDetailsService;
   /* public UserControl(UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }*/

    @PostMapping("/registration")
    User newUser(@RequestBody User newUser) {
        return userService.createNewUser(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new NotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id + " + id + " has been deleted success. ";
    }

    @PostMapping("/loginuser")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO) {
        User user = userService.authenticate(loginDTO);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }
}


