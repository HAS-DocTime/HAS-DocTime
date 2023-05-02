package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*")
public class UserController {

    private UserInterface userService;

    @Autowired
    public UserController(@Qualifier("userServiceImpl") UserInterface theUserService) {
        this.userService = theUserService;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.getAllUser();
        if(users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(users, HttpStatus.OK);
    }
    
    @GetMapping("findByEmail")
    public ResponseEntity<User> getUserByEmail(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(userEmail);
        if(user==null){
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("{patientId}")
    public ResponseEntity<User> getUser(@PathVariable("patientId") int id) throws AccessDeniedException {

        String authenticatedPatientId = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(authenticatedPatientId);
        User user = userService.getUser(id);
        if(!authenticatedPatientId.equals(user.getEmail())){
            throw new AccessDeniedException("You do not have access to this resource");
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
