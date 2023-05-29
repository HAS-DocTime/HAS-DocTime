package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.RegisterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "${port.address}")
public class RegisterController {

    @Autowired
    @Qualifier("registerServiceImpl")
    private RegisterInterface registerService;


    @PostMapping("/admin")
    public ResponseEntity<AuthenticationResponse> registerRequest(@RequestBody Admin admin) throws DoesNotExistException, MissingParameterException {
        return ResponseEntity.ok(registerService.registerAdmin(admin));

    }

    @PostMapping("/user")
    public ResponseEntity<AuthenticationResponse> registerRequest(@RequestBody User user) throws MissingParameterException, DoesNotExistException {
        return ResponseEntity.ok(registerService.registerUser(user));
    }

    @PostMapping("/doctor")
    public ResponseEntity<AuthenticationResponse> registerRequest(@RequestBody Doctor doctor) throws MissingParameterException, DoesNotExistException{
        return ResponseEntity.ok(registerService.registerDoctor(doctor));
    }
}
