package com.example.hotel.services;

import com.example.hotel.DTO.LoginDTO;
import com.example.hotel.configuratoins.NotFoundException;
import com.example.hotel.models.User;
import com.example.hotel.models.enums.Role;
import com.example.hotel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService{
    @Autowired
    private JavaMailSender mailSender;

    private String verificationCode = "";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createNewUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
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

    public ResponseEntity<?> update(User newUser, Long id){
        User user = userRepository.findById(id).map(user1 -> {
            user1.setEmail(newUser.getEmail());
            user1.setRoles(newUser.getRoles());
            return userRepository.save(user1);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id){
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
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

    public ResponseEntity<Void> sendCode(String email){
        if (email == null || email.isEmpty() || userRepository.findByEmail(email) != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        verificationCode = String.format("%06d", (int) (Math.random() * 999999));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mangwork960@gmail.com");
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText("Ваш код для регистрации в системе отеля VMang0: " + verificationCode);
        mailSender.send(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> verifyCode(String code){
        if (code.equals(verificationCode)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}






