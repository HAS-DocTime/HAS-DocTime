package com.spring.hasdoctime.controller;

import com.spring.hasdoctime.entity.*;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.LoginInterface;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * The LoginController class handles requests related to user login.
 */
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "${port.address}")
public class LoginController {

    /**
     * The port address value retrieved from configuration.
     */
    @Value("${port.address}")
    String portAddress;

    private final LoginInterface loginService;

    /**
     * Constructs a new LoginController with the specified LoginInterface.
     *
     * @param loginService the LoginInterface implementation
     */
    @Autowired
    public LoginController(@Qualifier("loginServiceImpl") LoginInterface loginService) {
        this.loginService = loginService;
    }

    /**
     * Handles the login request and returns the authentication response.
     *
     * @param loginDetail the LoginDetail object containing login details
     * @return the ResponseEntity with the AuthenticationResponse
     * @throws MissingParameterException if a required parameter is missing
     */
    @PostMapping("")
    public ResponseEntity<AuthenticationResponse> loginRequest(@RequestBody LoginDetail loginDetail) throws MissingParameterException {
        return ResponseEntity.ok(loginService.loginRequest(loginDetail));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<Void> sendEmailForForgotPassword(@RequestBody SendOtpEmail sendOtpEmail) throws MessagingException {

        if(loginService.sendEmailForForgotPassword(sendOtpEmail).equals(Boolean.TRUE)){
            return ResponseEntity.ok(null);
        }else{
            return ResponseEntity.badRequest().body(null);
        }

    }

    @PostMapping("/otpVerify")
    public ResponseEntity<Void> otpVerification(@RequestBody OtpRequestBody otpRequestBody){

        if(loginService.otpVerification(otpRequestBody).equals(Boolean.TRUE)){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/saveNewPassword")
    public ResponseEntity<Void> saveNewPassword(@RequestBody PasswordUpdateBody passwordUpdateBody){
        if(loginService.saveNewPassword(passwordUpdateBody).equals(Boolean.TRUE)){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().body(null);
    }

}
