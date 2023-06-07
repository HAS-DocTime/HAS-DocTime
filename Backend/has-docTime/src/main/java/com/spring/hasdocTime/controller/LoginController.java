package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.LoginInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;



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

    @PostMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loginService.refreshToken(request, response);
    }

}
