package com.example.hotel.controllers;

import com.example.hotel.DTO.LoginDTO;
import com.example.hotel.configuratoins.NotFoundException;
import com.example.hotel.models.User;
import com.example.hotel.repositories.UserRepository;
import com.example.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class UserControl {

    @Autowired
    private  UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/registration")
    User newUser(@RequestBody User newUser) {
        return userService.createNewUser(newUser);
    }

    @PostMapping("/add_manager")
    User newManager(@RequestBody User newUser) {
        return userService.createNewManager(newUser);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
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
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable Long id) {
       return userService.update(newUser, id);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
       return userService.delete(id);
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
        return userService.sendCode(email);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyCode(@RequestBody Map<String, String> request) {
        String code = request.get("verificationCode");
        return userService.verifyCode(code);
    }
}


