package com.kaveri.controller;

import com.kaveri.exception.RecordNotFoundException;
import com.kaveri.model.AuthRequest;
import com.kaveri.model.User;
import com.kaveri.service.UserServiceImpl;
import com.kaveri.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    public UserServiceImpl userServiceImpl;

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/savedata")
    public ResponseEntity<User> saveData(@RequestBody User user) {
        return new ResponseEntity<>(userServiceImpl.saveData(user), HttpStatus.CREATED);
    }

    @PostMapping("/savebulkdata")
    public ResponseEntity<List<User>> saveBulkData(@RequestBody List<User> users) {
        return new ResponseEntity<>(userServiceImpl.saveBulkOfData(users), HttpStatus.CREATED);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userServiceImpl.findAll());
    }

    @GetMapping("/findbyid/{userId}")
    public ResponseEntity<Optional<User>> findById(@PathVariable int userId) {
        return ResponseEntity.ok(userServiceImpl.findById(userId));
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<User>> sortByName() {
        return ResponseEntity.ok(userServiceImpl.findAll().stream().sorted(Comparator.comparing(User::getUserName).reversed()).toList());
    }

    @PutMapping("/updatedata/{userId}")
    public ResponseEntity<User> updateData(@PathVariable int userId, @RequestBody User user) {
        User user1 = userServiceImpl.findById(userId).orElseThrow(() -> new RecordNotFoundException("User Not Exist"));

        user1.setUserName(user.getUserName());
        user1.setUserPassword(user.getUserPassword());
        return ResponseEntity.ok(userServiceImpl.updateData(user1));

    }

    @DeleteMapping("/deletebyid/{userId}")
    public ResponseEntity<String> deleteById(@PathVariable int userId) {
        userServiceImpl.deleteById(userId);
        return ResponseEntity.ok("Delete Success..");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        userServiceImpl.deleteAll();
        return ResponseEntity.ok("Delete All Data Success..");
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getUserPassword()));

        } catch (Exception ex) {
            throw new Exception("Incorrect Username or Password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
}
