package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<User> getUserByEmail() throws DoesNotExistException{
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(userEmail);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("{patientId}")
    public ResponseEntity<User> getUser(@PathVariable("patientId") int id) throws AccessDeniedException, DoesNotExistException {

        String authenticatedPatientId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(id);
        System.out.println(authenticatedPatientId);
        System.out.println(user.getEmail());
//        if(!authenticatedPatientId.equals(user.getEmail())){
//            throw new AccessDeniedException("You do not have access to this resource");
//        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User theUser) throws MissingParameterException, DoesNotExistException {
        User user = userService.createUser(theUser);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User theUser) throws DoesNotExistException, MissingParameterException {
        User user =  userService.updateUser(id, theUser);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) throws DoesNotExistException{
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @GetMapping("patient")
    public ResponseEntity<List<User>> getPatients(){
        List<User> users = userService.getPatients();
        if(users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @GetMapping("patient/chronicIllness/{id}")
    public ResponseEntity<Set<User>> getPatientsByChronicIllnessId(@PathVariable("id") int id) throws DoesNotExistException{
        Set<User> users = userService.getPatientsByChronicIllnessId(id);
        if(users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(users, HttpStatus.OK);
    }
}
