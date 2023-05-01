package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.interfc.LoginInterface;
import com.spring.hasdocTime.interfc.RegisterInterface;
import com.spring.hasdocTime.utills.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*")
public class RegisterController {

    @Autowired
    @Qualifier("registerServiceImpl")
    private RegisterInterface registerService;


    @PostMapping("/admin")
    public ResponseEntity<AuthenticationResponse> registerRequest(@RequestBody Admin admin) {
        return ResponseEntity.ok(registerService.registerAdmin(admin));

    }

    @PostMapping("/user")
    public ResponseEntity<AuthenticationResponse> registerRequest(@RequestBody User user){
        return ResponseEntity.ok(registerService.registerUser(user));
    }

    @PostMapping("/doctor")
    public ResponseEntity<AuthenticationResponse> registerRequest(@RequestBody Doctor doctor){
        return ResponseEntity.ok(registerService.registerDoctor(doctor));
    }
}
