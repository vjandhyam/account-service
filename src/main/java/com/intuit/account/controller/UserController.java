package com.intuit.account.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.account.model.User;
import com.intuit.account.model.UserType;
import com.intuit.account.service.UserService;

@RestController
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String userId) {
        User response = userService.findByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/type/{userType}")
    public ResponseEntity<List<User>> getUser(@PathVariable("userType") UserType userType) {
        List<User> response = userService.findByUserType(userType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> allUsers = userService.findAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        if (!StringUtils.isEmpty(user.getId())) {
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User newUser = userService.updateUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
