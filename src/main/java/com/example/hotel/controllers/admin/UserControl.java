package com.example.hotel.controllers.admin;

import com.example.hotel.DTO.LoginDTO;
import com.example.hotel.configuratoins.NotFoundException;
import com.example.hotel.models.User;
import com.example.hotel.repositories.UserRepository;
import com.example.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class UserControl {

    @Autowired
    private  UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    private String verificationCode = "";

    @PostMapping("/registration")
    User newUser(@RequestBody User newUser) {
        return userService.createNewUser(newUser);
    }

    @PostMapping("/add_manager")
    User newManager(@RequestBody User newUser) {
        return userService.createNewManager(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/userlist")
    List<User> getUserList(){return userService.listUser();}

    @GetMapping("/managerlist")
    List<User> getManagerList() {return userService.listManager();}

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        String role = String.valueOf(newUser.getRoles());
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(newUser.getEmail());
                    user.setRoles(newUser.getRoles());
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
        if(user == null || userRepository.findByEmail(loginDTO.getEmail()) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/send-code")
    public ResponseEntity<Void> sendCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty() || userRepository.findByEmail(email) != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        verificationCode = String.format("%06d", (int) (Math.random() * 999999));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mangwork960@gmail.com");
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText("Ну привет мой сладкий пупсик, вот твой код для регистрации: " + verificationCode);
        mailSender.send(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyCode(@RequestBody Map<String, String> request) {
        String code = request.get("verificationCode");
        if (code.equals(verificationCode)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


