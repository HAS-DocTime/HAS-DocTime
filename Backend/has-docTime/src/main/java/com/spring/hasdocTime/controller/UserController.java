package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.UserInterface;
import com.spring.hasdocTime.security.AuthResponse;
import com.spring.hasdocTime.security.RegisterRequest;
import com.spring.hasdocTime.security.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin(value = "*")
public class UserController {

    private UserInterface userService;

    @Autowired
    public UserController(@Qualifier("userServiceImpl") UserInterface theUserService) {
        this.userService = theUserService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody User request
    ){
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.getAllUser();
        if(users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        User user = userService.getUser(id);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User theUser) {
        User user = userService.createUser(theUser);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User theUser) {
        User user =  userService.updateUser(id, theUser);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        if(userService.getUser(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
