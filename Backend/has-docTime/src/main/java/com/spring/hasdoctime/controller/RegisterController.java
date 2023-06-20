package com.spring.hasdoctime.controller;

import com.spring.hasdoctime.entity.*;
import com.spring.hasdoctime.exceptionhandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.RegisterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The RegisterController class handles requests related to user registration.
 */
@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "${port.address}")
public class RegisterController {

    @Autowired
    @Qualifier("registerServiceImpl")
    private RegisterInterface registerService;

    /**
     * Handles the registration request for an admin and returns the authentication response.
     *
     * @param admin the Admin object containing admin details
     * @return the ResponseEntity with the AuthenticationResponse
     * @throws DoesNotExistException     if the admin does not exist
     * @throws MissingParameterException if a required parameter is missing
     */
    @PostMapping("/admin")
    public ResponseEntity<AuthenticationResponse> registerRequest(@RequestBody Admin admin) throws DoesNotExistException, MissingParameterException {
        return ResponseEntity.ok(registerService.registerAdmin(admin));

    }

    /**
     * Handles the registration request for a user and returns the authentication response.
     *
     * @param user the User object containing user details
     * @return the ResponseEntity with the AuthenticationResponse
     * @throws MissingParameterException if a required parameter is missing
     * @throws DoesNotExistException     if the user does not exist
     */
    @PostMapping("/user")
    public ResponseEntity<AuthenticationResponse> registerRequest(@RequestBody User user) throws MissingParameterException, DoesNotExistException {
        return ResponseEntity.ok(registerService.registerUser(user));
    }

    /**
     * Handles the registration request for a doctor and returns the authentication response.
     *
     * @param doctor the Doctor object containing doctor details
     * @return the ResponseEntity with the AuthenticationResponse
     * @throws MissingParameterException if a required parameter is missing
     * @throws DoesNotExistException     if the doctor does not exist
     */
    @PostMapping("/doctor")
    public ResponseEntity<AuthenticationResponse> registerRequest(@RequestBody Doctor doctor) throws MissingParameterException, DoesNotExistException{
        return ResponseEntity.ok(registerService.registerDoctor(doctor));
    }
}
