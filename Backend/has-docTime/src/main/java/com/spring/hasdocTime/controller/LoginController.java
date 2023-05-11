package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.LoginInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

    private final LoginInterface loginService;

    @Autowired
    public LoginController(@Qualifier("loginServiceImpl") LoginInterface loginService) {
        this.loginService = loginService;
    }

    
    @PostMapping("")
    public ResponseEntity<AuthenticationResponse> loginRequest(@RequestBody LoginDetail loginDetail) throws MissingParameterException {
        return ResponseEntity.ok(loginService.loginRequest(loginDetail));
    }

    @PostMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loginService.refreshToken(request, response);
    }

}
